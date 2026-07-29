package com.cloud.optimizer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cloud_providers")
public class CloudProvider {

    @Id
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    public CloudProvider() {}

    public CloudProvider(String providerId, String providerName) {
        this.providerId = providerId;
        this.providerName = providerName;
    }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String providerId;
        private String providerName;

        public Builder providerId(String providerId) { this.providerId = providerId; return this; }
        public Builder providerName(String providerName) { this.providerName = providerName; return this; }

        public CloudProvider build() {
            return new CloudProvider(providerId, providerName);
        }
    }
}
