package hu.muhari.spring.logistics.service;

import java.util.List;
import java.util.Optional;

import hu.muhari.spring.logistics.config.LogisticsConfigProperties;
import hu.muhari.spring.logistics.model.Milestone;
import hu.muhari.spring.logistics.model.Section;
import hu.muhari.spring.logistics.model.TransportPlan;
import hu.muhari.spring.logistics.repository.TransportPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransportPlanService {

    TransportPlanRepository transportPlanRepository;
    SectionService sectionService;
    MilestoneService milestoneService;
    LogisticsConfigProperties config;

    public TransportPlanService(TransportPlanRepository transportPlanRepository,
                                SectionService sectionService, MilestoneService milestoneService, LogisticsConfigProperties config) {
        this.transportPlanRepository = transportPlanRepository;
        this.sectionService = sectionService;
        this.milestoneService = milestoneService;
        this.config = config;
    }

    public List<TransportPlan> getAllTransportPlans() {
        return transportPlanRepository.findAll();
    }

    public Optional<TransportPlan> findById(long id) {
        return transportPlanRepository.findById(id);
    }

    @Transactional
    public TransportPlan createNewTransportPlan(TransportPlan transportPlan) {
        TransportPlan newTransportPlan = transportPlanRepository.save(transportPlan);
        newTransportPlan.getSections().forEach(s -> s.setTransportPlan(newTransportPlan));
        return newTransportPlan;

    }

    @Transactional
    public TransportPlan modifyTransportPlan(TransportPlan transportPlan) {
        return transportPlanRepository.save(transportPlan);
    }

    @Transactional
    public void deleteAll() {
        sectionService.getAllSections().forEach(s -> s.setTransportPlan(null));
        getAllTransportPlans().forEach(t -> t.setSections(null));
        transportPlanRepository.deleteAll();
    }

    @Transactional
    public long registerDelay(long transportPlanId, long milestoneId, int delayInMinutes) {
        long newRevenue = changeRevenue(transportPlanId, delayInMinutes);
        setDelayInAffectedMilestones(transportPlanId, milestoneId, delayInMinutes);
        return newRevenue;
    }

    private long changeRevenue(long transportPlanId, int delayInMinutes) {
        TransportPlan transportPlan = transportPlanRepository.findById(transportPlanId).get();
        long adjustedRevenue = transportPlan.getExpectedRevenue();

        if (delayInMinutes < 30) {
            adjustedRevenue = transportPlan.getExpectedRevenue();
        } else if (delayInMinutes < 60) {
            adjustedRevenue *= (100 - config.getRevenueDropPercentage().getDelayOf30Minutes()) * 0.01;
        } else if (delayInMinutes < 120) {
            adjustedRevenue *= (100 - config.getRevenueDropPercentage().getDelayOf60Minutes()) * 0.01;
        } else {
            adjustedRevenue *= (100 - config.getRevenueDropPercentage().getDelayOf120Minutes()) * 0.01;
        }

        transportPlan.setExpectedRevenue(adjustedRevenue);

        return adjustedRevenue;
    }


    private void setDelayInAffectedMilestones(long transportPlanId, long milestoneId, int delay) {

        Milestone currentMilestone = milestoneService.findById(milestoneId).get();
        currentMilestone.setPlannedTime(currentMilestone.getPlannedTime().plusMinutes(delay));

        Section section = sectionService.findByMilestoneId(milestoneId).get();
        Milestone nextMilestone = null;

        if (section.getFromMilestone().equals(currentMilestone)) {
            nextMilestone = section.getToMilestone();
        } else {
            int nextSectionNumber = section.getNumberOfSection() + 1;
            Section nextSection = sectionService.findByTransportPlanIdAndNumber(transportPlanId, nextSectionNumber).orElse(null);
            if (nextSection != null) {
                nextMilestone = nextSection.getFromMilestone();
            }
        }

        if (nextMilestone != null) {
            nextMilestone.setPlannedTime(nextMilestone.getPlannedTime().plusMinutes(delay));
        }
    }

}
