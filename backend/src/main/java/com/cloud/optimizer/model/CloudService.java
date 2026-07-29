package com.cloud.optimizer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cloud_services")
public class CloudService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "service_id", updatable = false, nullable = false)
    private String serviceId;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "specifications", length = 1000)
    private String specifications;

    public CloudService() {}

    public CloudService(String serviceId, String providerId, String serviceName, String category, String specifications) {
        this.serviceId = serviceId;
        this.providerId = providerId;
        this.serviceName = serviceName;
        this.category = category;
        this.specifications = specifications;
    }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSpecifications() { return specifications; }
    public void setSpecifications(String specifications) { this.specifications = specifications; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String serviceId;
        private String providerId;
        private String serviceName;
        private String category;
        private String specifications;

        public Builder serviceId(String serviceId) { this.serviceId = serviceId; return this; }
        public Builder providerId(String providerId) { this.providerId = providerId; return this; }
        public Builder serviceName(String serviceName) { this.serviceName = serviceName; return this; }
        public Builder category(String category) { this.category = category; return this; }
        public Builder specifications(String specifications) { this.specifications = specifications; return this; }

        public CloudService build() {
            return new CloudService(serviceId, providerId, serviceName, category, specifications);
        }
    }
}
