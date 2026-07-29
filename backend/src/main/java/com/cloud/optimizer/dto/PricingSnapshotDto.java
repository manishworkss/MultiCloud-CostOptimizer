package com.cloud.optimizer.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PricingSnapshotDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String providerId;
    private String providerName;
    private String serviceName;
    private String category;
    private BigDecimal hourlyRate;
    private BigDecimal monthlyComputeCost;
    private BigDecimal storageCostPerGbMonth;
    private BigDecimal databaseMonthlyCost;
    private BigDecimal egressCostPerGb;
    private BigDecimal totalMonthlyCost;
    private BigDecimal totalYearlyCost;
    private String currency;
    private String region;

    public PricingSnapshotDto() {}

    public PricingSnapshotDto(String providerId, String providerName, String serviceName, String category, BigDecimal hourlyRate, BigDecimal monthlyComputeCost, BigDecimal storageCostPerGbMonth, BigDecimal databaseMonthlyCost, BigDecimal egressCostPerGb, BigDecimal totalMonthlyCost, BigDecimal totalYearlyCost, String currency, String region) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.serviceName = serviceName;
        this.category = category;
        this.hourlyRate = hourlyRate;
        this.monthlyComputeCost = monthlyComputeCost;
        this.storageCostPerGbMonth = storageCostPerGbMonth;
        this.databaseMonthlyCost = databaseMonthlyCost;
        this.egressCostPerGb = egressCostPerGb;
        this.totalMonthlyCost = totalMonthlyCost;
        this.totalYearlyCost = totalYearlyCost;
        this.currency = currency != null ? currency : "USD";
        this.region = region;
    }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public BigDecimal getMonthlyComputeCost() { return monthlyComputeCost; }
    public void setMonthlyComputeCost(BigDecimal monthlyComputeCost) { this.monthlyComputeCost = monthlyComputeCost; }

    public BigDecimal getStorageCostPerGbMonth() { return storageCostPerGbMonth; }
    public void setStorageCostPerGbMonth(BigDecimal storageCostPerGbMonth) { this.storageCostPerGbMonth = storageCostPerGbMonth; }

    public BigDecimal getDatabaseMonthlyCost() { return databaseMonthlyCost; }
    public void setDatabaseMonthlyCost(BigDecimal databaseMonthlyCost) { this.databaseMonthlyCost = databaseMonthlyCost; }

    public BigDecimal getEgressCostPerGb() { return egressCostPerGb; }
    public void setEgressCostPerGb(BigDecimal egressCostPerGb) { this.egressCostPerGb = egressCostPerGb; }

    public BigDecimal getTotalMonthlyCost() { return totalMonthlyCost; }
    public void setTotalMonthlyCost(BigDecimal totalMonthlyCost) { this.totalMonthlyCost = totalMonthlyCost; }

    public BigDecimal getTotalYearlyCost() { return totalYearlyCost; }
    public void setTotalYearlyCost(BigDecimal totalYearlyCost) { this.totalYearlyCost = totalYearlyCost; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String providerId;
        private String providerName;
        private String serviceName;
        private String category;
        private BigDecimal hourlyRate;
        private BigDecimal monthlyComputeCost;
        private BigDecimal storageCostPerGbMonth;
        private BigDecimal databaseMonthlyCost;
        private BigDecimal egressCostPerGb;
        private BigDecimal totalMonthlyCost;
        private BigDecimal totalYearlyCost;
        private String currency = "USD";
        private String region;

        public Builder providerId(String providerId) { this.providerId = providerId; return this; }
        public Builder providerName(String providerName) { this.providerName = providerName; return this; }
        public Builder serviceName(String serviceName) { this.serviceName = serviceName; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder hourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; return this; }
        public Builder monthlyComputeCost(BigDecimal monthlyComputeCost) { this.monthlyComputeCost = monthlyComputeCost; return this; }
        public Builder storageCostPerGbMonth(BigDecimal storageCostPerGbMonth) { this.storageCostPerGbMonth = storageCostPerGbMonth; return this; }
        public Builder databaseMonthlyCost(BigDecimal databaseMonthlyCost) { this.databaseMonthlyCost = databaseMonthlyCost; return this; }
        public Builder egressCostPerGb(BigDecimal egressCostPerGb) { this.egressCostPerGb = egressCostPerGb; return this; }
        public Builder totalMonthlyCost(BigDecimal totalMonthlyCost) { this.totalMonthlyCost = totalMonthlyCost; return this; }
        public Builder totalYearlyCost(BigDecimal totalYearlyCost) { this.totalYearlyCost = totalYearlyCost; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder region(String region) { this.region = region; return this; }

        public PricingSnapshotDto build() {
            return new PricingSnapshotDto(providerId, providerName, serviceName, category, hourlyRate, monthlyComputeCost, storageCostPerGbMonth, databaseMonthlyCost, egressCostPerGb, totalMonthlyCost, totalYearlyCost, currency, region);
        }
    }
}
