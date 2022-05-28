package hu.muhari.spring.logistics.controller;

import hu.muhari.spring.logistics.dto.DelayDto;
import hu.muhari.spring.logistics.service.MilestoneService;
import hu.muhari.spring.logistics.service.SectionService;
import hu.muhari.spring.logistics.service.TransportPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/transportPlans")
public class TransportPlanController {

    TransportPlanService transportPlanService;
    MilestoneService milestoneService;
    SectionService sectionService;

    public TransportPlanController(TransportPlanService transportPlanService, MilestoneService milestoneService,
                                   SectionService sectionService) {
        this.transportPlanService = transportPlanService;
        this.milestoneService = milestoneService;
        this.sectionService = sectionService;
    }

    @PostMapping("/{id}/delay")
    public void addDelayToATransportPlan(@PathVariable Long id, @RequestBody DelayDto delay) {
        if (transportPlanService.findById(id).isEmpty() ||
                milestoneService.findById(delay.getMilestoneId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (sectionService.findByTransportPlanAndMilestone(id, delay.getMilestoneId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        transportPlanService.registerDelay(id, delay.getMilestoneId(), delay.getDelayInMinutes());
    }
}
