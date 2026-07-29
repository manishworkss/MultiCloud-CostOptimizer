package com.cloud.optimizer.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pricing")
public class Pricing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pricing_id", updatable = false, nullable = false)
    private String pricingId;

    @Column(name = "service_id", nullable = false)
    private String serviceId;

    @Column(name = "monthly_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal monthlyPrice;

    @Column(name = "yearly_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal yearlyPrice;

    @Column(name = "currency", nullable = false)
    private String currency = "USD";

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Pricing() {}

    public Pricing(String pricingId, String serviceId, BigDecimal monthlyPrice, BigDecimal yearlyPrice, String currency, LocalDateTime updatedAt) {
        this.pricingId = pricingId;
        this.serviceId = serviceId;
        this.monthlyPrice = monthlyPrice;
        this.yearlyPrice = yearlyPrice;
        this.currency = currency != null ? currency : "USD";
        this.updatedAt = updatedAt;
    }

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getPricingId() { return pricingId; }
    public void setPricingId(String pricingId) { this.pricingId = pricingId; }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public BigDecimal getMonthlyPrice() { return monthlyPrice; }
    public void setMonthlyPrice(BigDecimal monthlyPrice) { this.monthlyPrice = monthlyPrice; }

    public BigDecimal getYearlyPrice() { return yearlyPrice; }
    public void setYearlyPrice(BigDecimal yearlyPrice) { this.yearlyPrice = yearlyPrice; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String pricingId;
        private String serviceId;
        private BigDecimal monthlyPrice;
        private BigDecimal yearlyPrice;
        private String currency = "USD";
        private LocalDateTime updatedAt;

        public Builder pricingId(String pricingId) { this.pricingId = pricingId; return this; }
        public Builder serviceId(String serviceId) { this.serviceId = serviceId; return this; }
        public Builder monthlyPrice(BigDecimal monthlyPrice) { this.monthlyPrice = monthlyPrice; return this; }
        public Builder yearlyPrice(BigDecimal yearlyPrice) { this.yearlyPrice = yearlyPrice; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Pricing build() {
            return new Pricing(pricingId, serviceId, monthlyPrice, yearlyPrice, currency, updatedAt);
        }
    }
}
