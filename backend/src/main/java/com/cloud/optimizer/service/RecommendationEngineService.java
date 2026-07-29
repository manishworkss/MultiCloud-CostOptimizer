package com.cloud.optimizer.service;

import com.cloud.optimizer.dto.PricingSnapshotDto;
import com.cloud.optimizer.dto.RecommendationResponseDto;
import com.cloud.optimizer.model.DeploymentRequest;
import com.cloud.optimizer.model.Recommendation;
import com.cloud.optimizer.repository.DeploymentRequestRepository;
import com.cloud.optimizer.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationEngineService {

    private final DeploymentRequestRepository deploymentRequestRepository;
    private final PricingAggregationService aggregationService;
    private final RecommendationRepository recommendationRepository;

    private static final Map<String, Double> VENDOR_SLA_MAP = Map.of(
            "AWS", 0.9999,
            "AZURE", 0.9995,
            "GCP", 0.9999,
            "OCI", 0.9995
    );

    public RecommendationEngineService(DeploymentRequestRepository deploymentRequestRepository,
                                       PricingAggregationService aggregationService,
                                       RecommendationRepository recommendationRepository) {
        this.deploymentRequestRepository = deploymentRequestRepository;
        this.aggregationService = aggregationService;
        this.recommendationRepository = recommendationRepository;
    }

    public List<RecommendationResponseDto> evaluateDeploymentRequest(String requestId) {
        DeploymentRequest request = deploymentRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Deployment request not found: " + requestId));

        List<PricingSnapshotDto> snapshots = aggregationService.fetchAllProviderPricing(request);
        if (snapshots.isEmpty()) {
            return Collections.emptyList();
        }

        // Find maximum monthly cost among providers
        BigDecimal maxMonthlyCost = snapshots.stream()
                .map(PricingSnapshotDto::getTotalMonthlyCost)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.valueOf(1.0));

        List<RecommendationResponseDto> results = new ArrayList<>();

        for (PricingSnapshotDto snapshot : snapshots) {
            BigDecimal monthlyCost = snapshot.getTotalMonthlyCost();
            BigDecimal yearlyCost = snapshot.getTotalYearlyCost();
            BigDecimal estimatedSavings = maxMonthlyCost.subtract(monthlyCost).setScale(2, RoundingMode.HALF_UP);

            // Cost Score: 1 - (Cp / Cmax)
            double costRatio = monthlyCost.doubleValue() / maxMonthlyCost.doubleValue();
            double costScore = Math.max(0.0, 1.0 - costRatio);

            // Spec Match Score: 0.95
            double specScore = 0.95;

            // Region SLA Uptime
            double slaScore = VENDOR_SLA_MAP.getOrDefault(snapshot.getProviderId().toUpperCase(), 0.999);

            // Recommendation Score Math: 0.60 * CostScore + 0.25 * SpecScore + 0.15 * SLAScore
            double totalScore = (0.60 * costScore + 0.25 * specScore + 0.15 * slaScore) * 100.0;
            BigDecimal finalScore = BigDecimal.valueOf(totalScore).setScale(2, RoundingMode.HALF_UP);

            // Save or update recommendation record
            Recommendation rec = Recommendation.builder()
                    .requestId(requestId)
                    .providerId(snapshot.getProviderId())
                    .totalMonthlyCost(monthlyCost)
                    .totalYearlyCost(yearlyCost)
                    .estimatedSavings(estimatedSavings)
                    .recommendationScore(finalScore)
                    .build();

            Recommendation saved = recommendationRepository.save(rec);

            results.add(RecommendationResponseDto.builder()
                    .recommendationId(saved.getRecommendationId())
                    .requestId(requestId)
                    .providerId(snapshot.getProviderId())
                    .providerName(snapshot.getProviderName())
                    .serviceName(snapshot.getServiceName())
                    .totalMonthlyCost(monthlyCost)
                    .totalYearlyCost(yearlyCost)
                    .estimatedSavings(estimatedSavings)
                    .recommendationScore(finalScore)
                    .regionSlaUptime(slaScore * 100.0)
                    .region(request.getRegion())
                    .build());
        }

        // Sort descending by recommendation score
        results.sort(Comparator.comparing(RecommendationResponseDto::getRecommendationScore).reversed());
        return results;
    }

    public List<RecommendationResponseDto> getRecommendationsForRequest(String requestId) {
        DeploymentRequest request = deploymentRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Deployment request not found: " + requestId));

        List<Recommendation> recs = recommendationRepository.findByRequestId(requestId);
        if (recs.isEmpty()) {
            return evaluateDeploymentRequest(requestId);
        }

        return recs.stream().map(rec -> {
            String providerName = getProviderDisplayName(rec.getProviderId());
            double sla = VENDOR_SLA_MAP.getOrDefault(rec.getProviderId().toUpperCase(), 0.999) * 100.0;
            return RecommendationResponseDto.builder()
                    .recommendationId(rec.getRecommendationId())
                    .requestId(requestId)
                    .providerId(rec.getProviderId())
                    .providerName(providerName)
                    .serviceName(rec.getProviderId() + " Compute & Storage")
                    .totalMonthlyCost(rec.getTotalMonthlyCost())
                    .totalYearlyCost(rec.getTotalYearlyCost())
                    .estimatedSavings(rec.getEstimatedSavings())
                    .recommendationScore(rec.getRecommendationScore())
                    .regionSlaUptime(sla)
                    .region(request.getRegion())
                    .build();
        }).sorted(Comparator.comparing(RecommendationResponseDto::getRecommendationScore).reversed())
          .collect(Collectors.toList());
    }

    private String getProviderDisplayName(String providerId) {
        return switch (providerId.toUpperCase()) {
            case "AWS" -> "Amazon Web Services";
            case "AZURE" -> "Microsoft Azure";
            case "GCP" -> "Google Cloud Platform";
            case "OCI" -> "Oracle Cloud Infrastructure";
            default -> providerId;
        };
    }
}
