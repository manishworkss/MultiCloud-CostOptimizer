package com.cloud.optimizer.dto;

import java.math.BigDecimal;

public class RecommendationResponseDto {

    private String recommendationId;
    private String requestId;
    private String providerId;
    private String providerName;
    private String serviceName;
    private BigDecimal totalMonthlyCost;
    private BigDecimal totalYearlyCost;
    private BigDecimal estimatedSavings;
    private BigDecimal recommendationScore;
    private double regionSlaUptime;
    private String region;

    public RecommendationResponseDto() {}

    public RecommendationResponseDto(String recommendationId, String requestId, String providerId, String providerName, String serviceName, BigDecimal totalMonthlyCost, BigDecimal totalYearlyCost, BigDecimal estimatedSavings, BigDecimal recommendationScore, double regionSlaUptime, String region) {
        this.recommendationId = recommendationId;
        this.requestId = requestId;
        this.providerId = providerId;
        this.providerName = providerName;
        this.serviceName = serviceName;
        this.totalMonthlyCost = totalMonthlyCost;
        this.totalYearlyCost = totalYearlyCost;
        this.estimatedSavings = estimatedSavings;
        this.recommendationScore = recommendationScore;
        this.regionSlaUptime = regionSlaUptime;
        this.region = region;
    }

    public String getRecommendationId() { return recommendationId; }
    public void setRecommendationId(String recommendationId) { this.recommendationId = recommendationId; }

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public BigDecimal getTotalMonthlyCost() { return totalMonthlyCost; }
    public void setTotalMonthlyCost(BigDecimal totalMonthlyCost) { this.totalMonthlyCost = totalMonthlyCost; }

    public BigDecimal getTotalYearlyCost() { return totalYearlyCost; }
    public void setTotalYearlyCost(BigDecimal totalYearlyCost) { this.totalYearlyCost = totalYearlyCost; }

    public BigDecimal getEstimatedSavings() { return estimatedSavings; }
    public void setEstimatedSavings(BigDecimal estimatedSavings) { this.estimatedSavings = estimatedSavings; }

    public BigDecimal getRecommendationScore() { return recommendationScore; }
    public void setRecommendationScore(BigDecimal recommendationScore) { this.recommendationScore = recommendationScore; }

    public double getRegionSlaUptime() { return regionSlaUptime; }
    public void setRegionSlaUptime(double regionSlaUptime) { this.regionSlaUptime = regionSlaUptime; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String recommendationId;
        private String requestId;
        private String providerId;
        private String providerName;
        private String serviceName;
        private BigDecimal totalMonthlyCost;
        private BigDecimal totalYearlyCost;
        private BigDecimal estimatedSavings;
        private BigDecimal recommendationScore;
        private double regionSlaUptime;
        private String region;

        public Builder recommendationId(String recommendationId) { this.recommendationId = recommendationId; return this; }
        public Builder requestId(String requestId) { this.requestId = requestId; return this; }
        public Builder providerId(String providerId) { this.providerId = providerId; return this; }
        public Builder providerName(String providerName) { this.providerName = providerName; return this; }
        public Builder serviceName(String serviceName) { this.serviceName = serviceName; return this; }
        public Builder totalMonthlyCost(BigDecimal totalMonthlyCost) { this.totalMonthlyCost = totalMonthlyCost; return this; }
        public Builder totalYearlyCost(BigDecimal totalYearlyCost) { this.totalYearlyCost = totalYearlyCost; return this; }
        public Builder estimatedSavings(BigDecimal estimatedSavings) { this.estimatedSavings = estimatedSavings; return this; }
        public Builder recommendationScore(BigDecimal recommendationScore) { this.recommendationScore = recommendationScore; return this; }
        public Builder regionSlaUptime(double regionSlaUptime) { this.regionSlaUptime = regionSlaUptime; return this; }
        public Builder region(String region) { this.region = region; return this; }

        public RecommendationResponseDto build() {
            return new RecommendationResponseDto(recommendationId, requestId, providerId, providerName, serviceName, totalMonthlyCost, totalYearlyCost, estimatedSavings, recommendationScore, regionSlaUptime, region);
        }
    }
}
