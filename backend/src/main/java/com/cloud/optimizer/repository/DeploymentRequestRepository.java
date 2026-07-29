package com.cloud.optimizer.repository;

import com.cloud.optimizer.model.DeploymentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeploymentRequestRepository extends JpaRepository<DeploymentRequest, String> {
    List<DeploymentRequest> findByProjectId(String projectId);
}
