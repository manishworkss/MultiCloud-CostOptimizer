package com.cloud.optimizer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cloud_providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudProvider {

    @Id
    @Column(name = "provider_id", nullable = false)
    private String providerId; // e.g. "AWS", "AZURE", "GCP", "OCI"

    @Column(name = "provider_name", nullable = false)
    private String providerName; // e.g. "Amazon Web Services", "Microsoft Azure", "Google Cloud Platform", "Oracle Cloud Infrastructure"
}
