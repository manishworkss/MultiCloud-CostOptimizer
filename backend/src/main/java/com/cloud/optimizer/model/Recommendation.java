package com.cloud.optimizer.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recommendation_id", updatable = false, nullable = false)
    private String recommendationId;

    @Column(name = "request_id", nullable = false)
    private String requestId;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "total_monthly_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalMonthlyCost;

    @Column(name = "total_yearly_cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalYearlyCost;

    @Column(name = "estimated_savings", nullable = false, precision = 12, scale = 2)
    private BigDecimal estimatedSavings;

    @Column(name = "recommendation_score", nullable = false, precision = 5, scale = 2)
    private BigDecimal recommendationScore; // e.g. 94.50

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
