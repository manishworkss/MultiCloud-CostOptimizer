package com.cloud.optimizer.client;

import com.cloud.optimizer.dto.PricingSnapshotDto;
import com.cloud.optimizer.model.DeploymentRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class GcpPricingClient implements CloudPricingClient {

    @Override
    public String getProviderId() {
        return "GCP";
    }

    @Override
    public String getProviderName() {
        return "Google Cloud Platform";
    }

    @Override
    public PricingSnapshotDto fetchLivePricing(DeploymentRequest request) {
        // Real GCP Compute Engine e2-standard & Persistent Disk rates
        BigDecimal hourlyRate = new BigDecimal("0.0670"); // GCP e2-medium
        BigDecimal storageCostPerGb = new BigDecimal("0.040"); // GCP pd-balanced SSD
        BigDecimal dbMonthlyCost = new BigDecimal("85.00"); // Cloud SQL PostgreSQL
        BigDecimal egressCostPerGb = new BigDecimal("0.085"); // GCP Network Egress

        int cpuCount = parseNumber(request.getCpu(), 4);
        int storageGb = parseNumber(request.getStorage(), 200);
        int bandwidthGb = parseNumber(request.getBandwidth(), 500);

        BigDecimal monthlyCompute = hourlyRate.multiply(BigDecimal.valueOf(cpuCount * 0.5 + 1)).multiply(new BigDecimal("730")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal monthlyStorage = storageCostPerGb.multiply(BigDecimal.valueOf(storageGb)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal monthlyEgress = egressCostPerGb.multiply(BigDecimal.valueOf(bandwidthGb)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalMonthly = monthlyCompute.add(monthlyStorage).add(dbMonthlyCost).add(monthlyEgress);
        BigDecimal totalYearly = totalMonthly.multiply(new BigDecimal("12"));

        return PricingSnapshotDto.builder()
                .providerId(getProviderId())
                .providerName(getProviderName())
                .serviceName("e2-standard / Compute Engine (GCP)")
                .category("COMPUTE_STORAGE_DB")
                .hourlyRate(hourlyRate)
                .monthlyComputeCost(monthlyCompute)
                .storageCostPerGbMonth(storageCostPerGb)
                .databaseMonthlyCost(dbMonthlyCost)
                .egressCostPerGb(egressCostPerGb)
                .totalMonthlyCost(totalMonthly)
                .totalYearlyCost(totalYearly)
                .currency("USD")
                .region(request.getRegion())
                .build();
    }

    private int parseNumber(String val, int defaultVal) {
        if (val == null) return defaultVal;
        try {
            String digits = val.replaceAll("[^0-9]", "");
            return digits.isEmpty() ? defaultVal : Integer.parseInt(digits);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
