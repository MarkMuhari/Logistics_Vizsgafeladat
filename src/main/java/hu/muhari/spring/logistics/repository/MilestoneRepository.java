package hu.muhari.spring.logistics.repository;

import hu.muhari.spring.logistics.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    List<Milestone> findByAddressId(Long id);
}
