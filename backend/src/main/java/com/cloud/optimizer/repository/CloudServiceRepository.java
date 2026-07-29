package com.cloud.optimizer.repository;

import com.cloud.optimizer.model.CloudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudServiceRepository extends JpaRepository<CloudService, String> {
    List<CloudService> findByProviderId(String providerId);
    List<CloudService> findByCategory(String category);
}
