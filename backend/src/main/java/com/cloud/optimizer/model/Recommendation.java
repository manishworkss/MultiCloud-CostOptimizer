package com.cloud.optimizer.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
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
    private BigDecimal recommendationScore;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Recommendation() {}

    public Recommendation(String recommendationId, String requestId, String providerId, BigDecimal totalMonthlyCost, BigDecimal totalYearlyCost, BigDecimal estimatedSavings, BigDecimal recommendationScore, LocalDateTime createdAt) {
        this.recommendationId = recommendationId;
        this.requestId = requestId;
        this.providerId = providerId;
        this.totalMonthlyCost = totalMonthlyCost;
        this.totalYearlyCost = totalYearlyCost;
        this.estimatedSavings = estimatedSavings;
        this.recommendationScore = recommendationScore;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public String getRecommendationId() { return recommendationId; }
    public void setRecommendationId(String recommendationId) { this.recommendationId = recommendationId; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public BigDecimal getTotalMonthlyCost() { return totalMonthlyCost; }
    public void setTotalMonthlyCost(BigDecimal totalMonthlyCost) { this.totalMonthlyCost = totalMonthlyCost; }

    public BigDecimal getTotalYearlyCost() { return totalYearlyCost; }
    public void setTotalYearlyCost(BigDecimal totalYearlyCost) { this.totalYearlyCost = totalYearlyCost; }

    public BigDecimal getEstimatedSavings() { return estimatedSavings; }
    public void setEstimatedSavings(BigDecimal estimatedSavings) { this.estimatedSavings = estimatedSavings; }

    public BigDecimal getRecommendationScore() { return recommendationScore; }
    public void setRecommendationScore(BigDecimal recommendationScore) { this.recommendationScore = recommendationScore; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String recommendationId;
        private String requestId;
        private String providerId;
        private BigDecimal totalMonthlyCost;
        private BigDecimal totalYearlyCost;
        private BigDecimal estimatedSavings;
        private BigDecimal recommendationScore;
        private LocalDateTime createdAt;

        public Builder recommendationId(String recommendationId) { this.recommendationId = recommendationId; return this; }
        public Builder requestId(String requestId) { this.requestId = requestId; return this; }
        public Builder providerId(String providerId) { this.providerId = providerId; return this; }
        public Builder totalMonthlyCost(BigDecimal totalMonthlyCost) { this.totalMonthlyCost = totalMonthlyCost; return this; }
        public Builder totalYearlyCost(BigDecimal totalYearlyCost) { this.totalYearlyCost = totalYearlyCost; return this; }
        public Builder estimatedSavings(BigDecimal estimatedSavings) { this.estimatedSavings = estimatedSavings; return this; }
        public Builder recommendationScore(BigDecimal recommendationScore) { this.recommendationScore = recommendationScore; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Recommendation build() {
            return new Recommendation(recommendationId, requestId, providerId, totalMonthlyCost, totalYearlyCost, estimatedSavings, recommendationScore, createdAt);
        }
    }
}
