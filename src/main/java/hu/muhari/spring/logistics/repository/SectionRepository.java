package hu.muhari.spring.logistics.repository;

import hu.muhari.spring.logistics.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query("SELECT s FROM Section s WHERE s.transportPlan.id = :transportPlanId AND (s.fromMilestone.id = :milestoneId " +
            "OR s.toMilestone.id = :milestoneId)")
    List<Section> findByTransportPlanAndMilestone(long transportPlanId, long milestoneId);

    @Query("SELECT s FROM Section s WHERE s.fromMilestone.id = :milestoneId OR s.toMilestone.id = :milestoneId")
    Optional<Section> findByMilestoneId(long milestoneId);

    @Query("SELECT s FROM Section s WHERE s.transportPlan.id = :transportPlanId AND s.numberOfSection = :number")
    Optional<Section> findByTransportPlanAndNumber(long transportPlanId, int number);


}
