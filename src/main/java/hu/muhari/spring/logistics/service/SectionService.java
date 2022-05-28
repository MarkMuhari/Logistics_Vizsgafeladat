package hu.muhari.spring.logistics.service;

import java.util.List;
import java.util.Optional;

import hu.muhari.spring.logistics.model.Section;
import hu.muhari.spring.logistics.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectionService {


    SectionRepository sectionRepository;
    MilestoneService milestoneService;

    public SectionService(SectionRepository sectionRepository, MilestoneService milestoneService) {
        this.sectionRepository = sectionRepository;
        this.milestoneService = milestoneService;
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Optional<Section> findById(long id) {
        return sectionRepository.findById(id);
    }

    public Optional<Section> findByTransportPlanIdAndNumber(long id, int number) {
        return sectionRepository.findByTransportPlanAndNumber(id, number);
    }

    public List<Section> findByTransportPlanAndMilestone(long transportPlanId, long milestoneId) {
        return sectionRepository.findByTransportPlanAndMilestone(transportPlanId, milestoneId);
    }

    public Optional<Section> findByMilestoneId(long milestoneId) {
        return sectionRepository.findByMilestoneId(milestoneId);
    }

    @Transactional
    public Section createSection(Section section) {
        return sectionRepository.save(section);
    }

    @Transactional
    public void deleteAll() {
        getAllSections().forEach(s -> s.setFromMilestone(null));
        getAllSections().forEach(s -> s.setToMilestone(null));
        sectionRepository.deleteAll();
    }

}
