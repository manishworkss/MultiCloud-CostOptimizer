package com.cloud.optimizer.controller;

import com.cloud.optimizer.dto.RecommendationResponseDto;
import com.cloud.optimizer.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RecommendationController {

    private final RecommendationEngineService recommendationEngineService;

    public RecommendationController(RecommendationEngineService recommendationEngineService) {
        this.recommendationEngineService = recommendationEngineService;
    }

    @PostMapping("/requests/{requestId}/evaluate")
    public ResponseEntity<List<RecommendationResponseDto>> evaluateRequest(@PathVariable String requestId) {
        return ResponseEntity.ok(recommendationEngineService.evaluateDeploymentRequest(requestId));
    }

    @GetMapping("/requests/{requestId}/recommendations")
    public ResponseEntity<List<RecommendationResponseDto>> getRecommendations(@PathVariable String requestId) {
        return ResponseEntity.ok(recommendationEngineService.getRecommendationsForRequest(requestId));
    }
}
