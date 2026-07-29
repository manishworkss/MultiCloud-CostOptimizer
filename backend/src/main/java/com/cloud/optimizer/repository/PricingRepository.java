package com.cloud.optimizer.repository;

import com.cloud.optimizer.model.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, String> {
    Optional<Pricing> findByServiceId(String serviceId);
}
