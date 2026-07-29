package com.cloud.optimizer.dto;

import jakarta.validation.constraints.NotBlank;

public class ProjectRequest {

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Application type is required")
    private String applicationType;

    public ProjectRequest() {}

    public ProjectRequest(String projectName, String applicationType) {
        this.projectName = projectName;
        this.applicationType = applicationType;
    }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getApplicationType() { return applicationType; }
    public void setApplicationType(String applicationType) { this.applicationType = applicationType; }
}
