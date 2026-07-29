package com.cloud.optimizer.repository;

import com.cloud.optimizer.model.CloudProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudProviderRepository extends JpaRepository<CloudProvider, String> {
}
