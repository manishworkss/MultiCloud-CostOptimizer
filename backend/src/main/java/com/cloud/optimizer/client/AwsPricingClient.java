package com.cloud.optimizer.client;

import com.cloud.optimizer.dto.PricingSnapshotDto;
import com.cloud.optimizer.model.DeploymentRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class AwsPricingClient implements CloudPricingClient {

    @Override
    public String getProviderId() {
        return "AWS";
    }

    @Override
    public String getProviderName() {
        return "Amazon Web Services";
    }

    @Override
    public PricingSnapshotDto fetchLivePricing(DeploymentRequest request) {
        // Real AWS EC2 t3/t4g rates & EBS gp3 storage pricing
        BigDecimal hourlyRate = new BigDecimal("0.0832"); // AWS t3.medium
        BigDecimal storageCostPerGb = new BigDecimal("0.080"); // EBS gp3 SSD
        BigDecimal dbMonthlyCost = new BigDecimal("98.00"); // AWS RDS PostgreSQL db.t3.medium
        BigDecimal egressCostPerGb = new BigDecimal("0.090"); // AWS Data Transfer Out

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
                .serviceName("t3.medium / EC2 (AWS)")
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
