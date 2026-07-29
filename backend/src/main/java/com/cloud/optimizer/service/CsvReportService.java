package com.cloud.optimizer.service;

import com.cloud.optimizer.dto.RecommendationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvReportService {

    private final RecommendationEngineService recommendationEngineService;

    public CsvReportService(RecommendationEngineService recommendationEngineService) {
        this.recommendationEngineService = recommendationEngineService;
    }

    public String generateCsvReport(String requestId) {
        List<RecommendationResponseDto> recommendations = recommendationEngineService.getRecommendationsForRequest(requestId);

        StringBuilder sb = new StringBuilder();
        sb.append("Provider ID,Provider Name,Service Name,Region,Total Monthly Cost ($),Total Yearly Cost ($),Estimated Savings ($),Recommendation Score,SLA Uptime (%)\n");

        for (RecommendationResponseDto dto : recommendations) {
            sb.append(escapeCsv(dto.getProviderId())).append(",")
              .append(escapeCsv(dto.getProviderName())).append(",")
              .append(escapeCsv(dto.getServiceName())).append(",")
              .append(escapeCsv(dto.getRegion())).append(",")
              .append(dto.getTotalMonthlyCost()).append(",")
              .append(dto.getTotalYearlyCost()).append(",")
              .append(dto.getEstimatedSavings()).append(",")
              .append(dto.getRecommendationScore()).append(",")
              .append(dto.getRegionSlaUptime()).append("\n");
        }

        return sb.toString();
    }

    private String escapeCsv(String input) {
        if (input == null) return "";
        if (input.contains(",") || input.contains("\"") || input.contains("\n")) {
            return "\"" + input.replace("\"", "\"\"") + "\"";
        }
        return input;
    }
}
