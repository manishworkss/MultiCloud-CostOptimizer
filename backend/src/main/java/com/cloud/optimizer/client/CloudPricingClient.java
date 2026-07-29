package com.cloud.optimizer.client;

import com.cloud.optimizer.dto.PricingSnapshotDto;
import com.cloud.optimizer.model.DeploymentRequest;

public interface CloudPricingClient {
    String getProviderId();
    String getProviderName();
    PricingSnapshotDto fetchLivePricing(DeploymentRequest request);
}
