package com.cloud.optimizer.controller;

import com.cloud.optimizer.dto.ProjectRequest;
import com.cloud.optimizer.model.DeploymentRequest;
import com.cloud.optimizer.model.Project;
import com.cloud.optimizer.model.User;
import com.cloud.optimizer.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@AuthenticationPrincipal User user,
                                                 @Valid @RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(user.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getUserProjects(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.getUserProjects(user.getUserId()));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable String projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @PostMapping("/{projectId}/requests")
    public ResponseEntity<DeploymentRequest> createDeploymentRequest(@PathVariable String projectId,
                                                                      @Valid @RequestBody DeploymentRequest request) {
        return ResponseEntity.ok(projectService.createDeploymentRequest(projectId, request));
    }

    @GetMapping("/{projectId}/requests")
    public ResponseEntity<List<DeploymentRequest>> getProjectRequests(@PathVariable String projectId) {
        return ResponseEntity.ok(projectService.getProjectRequests(projectId));
    }
}
