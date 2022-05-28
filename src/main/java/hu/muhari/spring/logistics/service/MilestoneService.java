package hu.muhari.spring.logistics.service;

import hu.muhari.spring.logistics.model.Milestone;
import hu.muhari.spring.logistics.repository.MilestoneRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
@Service
public class MilestoneService {

    @Autowired
    MilestoneRepository milestoneRepository;

    public List<Milestone> getAllMilestones() {
        return milestoneRepository.findAll();
    }

    public List<Milestone> findByAddressId(long id) {
        return milestoneRepository.findByAddressId(id);
    }

    public Optional<Milestone> findById(long id) {
        return milestoneRepository.findById(id);
    }

    @Transactional
    public Milestone createMilestone(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    @Transactional
    public void deleteAll() {
        getAllMilestones().stream().forEach(m -> m.setAddress(null));
        milestoneRepository.deleteAll();
    }

}
