package com.cloud.optimizer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deployment_requests")
public class DeploymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "request_id", updatable = false, nullable = false)
    private String requestId;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "cpu", nullable = false)
    private String cpu;

    @Column(name = "ram", nullable = false)
    private String ram;

    @Column(name = "storage", nullable = false)
    private String storage;

    @Column(name = "operating_system", nullable = false)
    private String operatingSystem;

    @Column(name = "database_type", nullable = false)
    private String databaseType;

    @Column(name = "bandwidth", nullable = false)
    private String bandwidth;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "expected_users")
    private Integer expectedUsers;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public DeploymentRequest() {}

    public DeploymentRequest(String requestId, String projectId, String cpu, String ram, String storage, String operatingSystem, String databaseType, String bandwidth, String region, Integer expectedUsers, LocalDateTime createdAt) {
        this.requestId = requestId;
        this.projectId = projectId;
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.operatingSystem = operatingSystem;
        this.databaseType = databaseType;
        this.bandwidth = bandwidth;
        this.region = region;
        this.expectedUsers = expectedUsers;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getCpu() { return cpu; }
    public void setCpu(String cpu) { this.cpu = cpu; }

    public String getRam() { return ram; }
    public void setRam(String ram) { this.ram = ram; }

    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }

    public String getOperatingSystem() { return operatingSystem; }
    public void setOperatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; }

    public String getDatabaseType() { return databaseType; }
    public void setDatabaseType(String databaseType) { this.databaseType = databaseType; }

    public String getBandwidth() { return bandwidth; }
    public void setBandwidth(String bandwidth) { this.bandwidth = bandwidth; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Integer getExpectedUsers() { return expectedUsers; }
    public void setExpectedUsers(Integer expectedUsers) { this.expectedUsers = expectedUsers; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String requestId;
        private String projectId;
        private String cpu;
        private String ram;
        private String storage;
        private String operatingSystem;
        private String databaseType;
        private String bandwidth;
        private String region;
        private Integer expectedUsers;
        private LocalDateTime createdAt;

        public Builder requestId(String requestId) { this.requestId = requestId; return this; }
        public Builder projectId(String projectId) { this.projectId = projectId; return this; }
        public Builder cpu(String cpu) { this.cpu = cpu; return this; }
        public Builder ram(String ram) { this.ram = ram; return this; }
        public Builder storage(String storage) { this.storage = storage; return this; }
        public Builder operatingSystem(String operatingSystem) { this.operatingSystem = operatingSystem; return this; }
        public Builder databaseType(String databaseType) { this.databaseType = databaseType; return this; }
        public Builder bandwidth(String bandwidth) { this.bandwidth = bandwidth; return this; }
        public Builder region(String region) { this.region = region; return this; }
        public Builder expectedUsers(Integer expectedUsers) { this.expectedUsers = expectedUsers; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public DeploymentRequest build() {
            return new DeploymentRequest(requestId, projectId, cpu, ram, storage, operatingSystem, databaseType, bandwidth, region, expectedUsers, createdAt);
        }
    }
}
