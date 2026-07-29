package com.cloud.optimizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_id", updatable = false, nullable = false)
    private String projectId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "application_type", nullable = false)
    private String applicationType;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Project() {}

    public Project(String projectId, String userId, String projectName, String applicationType, LocalDateTime createdAt) {
        this.projectId = projectId;
        this.userId = userId;
        this.projectName = projectName;
        this.applicationType = applicationType;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getApplicationType() { return applicationType; }
    public void setApplicationType(String applicationType) { this.applicationType = applicationType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String projectId;
        private String userId;
        private String projectName;
        private String applicationType;
        private LocalDateTime createdAt;

        public Builder projectId(String projectId) { this.projectId = projectId; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder projectName(String projectName) { this.projectName = projectName; return this; }
        public Builder applicationType(String applicationType) { this.applicationType = applicationType; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Project build() {
            return new Project(projectId, userId, projectName, applicationType, createdAt);
        }
    }
}
