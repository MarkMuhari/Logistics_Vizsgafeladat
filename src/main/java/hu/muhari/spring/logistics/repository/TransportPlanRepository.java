package hu.muhari.spring.logistics.repository;

import hu.muhari.spring.logistics.model.TransportPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {
}
