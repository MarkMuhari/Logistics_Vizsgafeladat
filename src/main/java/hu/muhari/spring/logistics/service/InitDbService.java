package hu.muhari.spring.logistics.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hu.muhari.spring.logistics.model.Address;
import hu.muhari.spring.logistics.model.Milestone;
import hu.muhari.spring.logistics.model.Section;
import hu.muhari.spring.logistics.model.TransportPlan;
import org.springframework.stereotype.Service;

@Service
public class InitDbService {

    AddressService addressService;
    MilestoneService milestoneService;
    SectionService sectionService;
    TransportPlanService transportPlanService;

    public InitDbService(AddressService addressService, MilestoneService milestoneService, SectionService sectionService, TransportPlanService transportPlanService) {
        this.addressService = addressService;
        this.milestoneService = milestoneService;
        this.sectionService = sectionService;
        this.transportPlanService = transportPlanService;
    }

    public TransportPlan init() {
        transportPlanService.deleteAll();
        sectionService.deleteAll();
        milestoneService.deleteAll();
        addressService.deleteAll();

        Address address1 = addressService
                .createAddress(new Address(1, "HU", "Budapest", "Lónyay utca", "1093",
                        "7", 1.45, 1.45));
        Address address2 = addressService
                .createAddress(new Address(2, "HU", "Budapest", "Lenkey utca", "1056",
                        "7", 1.45, 1.45));
        Address address3 = addressService
                .createAddress(new Address(3, "HU", "Budapest", "Lenkey tér", "1097",
                        "7", 1.45, 1.45));
        Address address4 = addressService
                .createAddress(new Address(4, "DE", "Berlin",
                        "Lenkey kőz", "0123", "5", 1.45, 1.45));
        Address address5 = addressService
                .createAddress(new Address(5, "DE", "Berlin", "Lenkey utca", "0123",
                        "5", 2.45, 2.45));

        Milestone milestone1 = milestoneService
                .createMilestone(new Milestone(1, address1,
                        LocalDateTime.of(2021, 05, 10, 1, 0)));
        Milestone milestone2 = milestoneService
                .createMilestone(new Milestone(2, address2,
                        LocalDateTime.of(2021, 05, 11, 2, 0)));
        Milestone milestone3 = milestoneService
                .createMilestone(new Milestone(3, address2,
                        LocalDateTime.of(2021, 05, 11, 2, 0)));
        Milestone milestone4 = milestoneService
                .createMilestone(new Milestone(4, address3,
                        LocalDateTime.of(2021, 05, 12, 1, 0)));
        Milestone milestone5 = milestoneService
                .createMilestone(new Milestone(5, address3,
                        LocalDateTime.of(2021, 05, 12, 2, 0)));
        Milestone milestone6 = milestoneService
                .createMilestone(new Milestone(6, address4,
                        LocalDateTime.of(2021, 05, 13, 1, 0)));
        Milestone milestone7 = milestoneService
                .createMilestone(new Milestone(7, address4,
                        LocalDateTime.of(2021, 05, 13, 2, 0)));
        Milestone milestone8 = milestoneService
                .createMilestone(new Milestone(8, address5,
                        LocalDateTime.of(2021, 05, 14, 1, 0)));

        List<Section> sections = new ArrayList<>();

        sections.add(sectionService.createSection(new Section(1, milestone1, milestone2,
                0, null)));
        sections.add(sectionService.createSection(new Section(2, milestone3, milestone4,
                1, null)));
        sections.add(sectionService.createSection(new Section(3, milestone5, milestone6,
                2, null)));
        sections.add(sectionService.createSection(new Section(4, milestone7, milestone8,
                3, null)));

        transportPlanService.createNewTransportPlan(new TransportPlan(1, sections, 10_500L));
        return transportPlanService.createNewTransportPlan(new TransportPlan(2, sections, 10_000L));
    }
}
