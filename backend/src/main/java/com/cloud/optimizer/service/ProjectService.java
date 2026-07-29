package com.cloud.optimizer.service;

import com.cloud.optimizer.dto.ProjectRequest;
import com.cloud.optimizer.model.DeploymentRequest;
import com.cloud.optimizer.model.Project;
import com.cloud.optimizer.repository.DeploymentRequestRepository;
import com.cloud.optimizer.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DeploymentRequestRepository deploymentRequestRepository;

    public ProjectService(ProjectRepository projectRepository, DeploymentRequestRepository deploymentRequestRepository) {
        this.projectRepository = projectRepository;
        this.deploymentRequestRepository = deploymentRequestRepository;
    }

    public Project createProject(String userId, ProjectRequest request) {
        Project project = Project.builder()
                .userId(userId)
                .projectName(request.getProjectName())
                .applicationType(request.getApplicationType())
                .build();
        return projectRepository.save(project);
    }

    public List<Project> getUserProjects(String userId) {
        return projectRepository.findByUserId(userId);
    }

    public Project getProjectById(String projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + projectId));
    }

    public DeploymentRequest createDeploymentRequest(String projectId, DeploymentRequest request) {
        Project project = getProjectById(projectId);
        request.setProjectId(project.getProjectId());
        return deploymentRequestRepository.save(request);
    }

    public List<DeploymentRequest> getProjectRequests(String projectId) {
        return deploymentRequestRepository.findByProjectId(projectId);
    }
}
