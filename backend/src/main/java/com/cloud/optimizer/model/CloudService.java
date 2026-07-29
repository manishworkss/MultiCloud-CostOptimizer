package com.cloud.optimizer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cloud_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "service_id", updatable = false, nullable = false)
    private String serviceId;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "service_name", nullable = false)
    private String serviceName; // e.g. "EC2 t3.medium", "Standard_B2s", "e2-medium"

    @Column(name = "category", nullable = false)
    private String category; // e.g. COMPUTE, STORAGE, DATABASE, BANDWIDTH

    @Column(name = "specifications", length = 1000)
    private String specifications; // JSON or formatted specs string
}
