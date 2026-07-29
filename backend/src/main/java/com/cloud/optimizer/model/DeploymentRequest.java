package com.cloud.optimizer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "deployment_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeploymentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "request_id", updatable = false, nullable = false)
    private String requestId;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "cpu", nullable = false)
    private String cpu; // e.g. "4 Cores"

    @Column(name = "ram", nullable = false)
    private String ram; // e.g. "16 GB"

    @Column(name = "storage", nullable = false)
    private String storage; // e.g. "200 GB SSD"

    @Column(name = "operating_system", nullable = false)
    private String operatingSystem; // e.g. "Ubuntu Linux 22.04"

    @Column(name = "database_type", nullable = false)
    private String databaseType; // e.g. "PostgreSQL"

    @Column(name = "bandwidth", nullable = false)
    private String bandwidth; // e.g. "500 GB"

    @Column(name = "region", nullable = false)
    private String region; // e.g. "US-East (N. Virginia)"

    @Column(name = "expected_users")
    private Integer expectedUsers;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
