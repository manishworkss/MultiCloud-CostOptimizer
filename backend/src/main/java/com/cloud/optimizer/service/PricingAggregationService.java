package com.cloud.optimizer.service;

import com.cloud.optimizer.client.CloudPricingClient;
import com.cloud.optimizer.dto.PricingSnapshotDto;
import com.cloud.optimizer.model.DeploymentRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PricingAggregationService {

    private final List<CloudPricingClient> pricingClients;
    private final PriceCacheService priceCacheService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    public PricingAggregationService(List<CloudPricingClient> pricingClients, PriceCacheService priceCacheService) {
        this.pricingClients = pricingClients;
        this.priceCacheService = priceCacheService;
    }

    public List<PricingSnapshotDto> fetchAllProviderPricing(DeploymentRequest request) {
        List<CompletableFuture<PricingSnapshotDto>> futures = new ArrayList<>();

        for (CloudPricingClient client : pricingClients) {
            CompletableFuture<PricingSnapshotDto> future = CompletableFuture.supplyAsync(() -> {
                String cacheKey = priceCacheService.buildCacheKey(client.getProviderId(), request.getRegion(), request.getCpu(), request.getRam());
                
                // 1. Try Redis cache
                PricingSnapshotDto cached = priceCacheService.getCachedPrice(cacheKey);
                if (cached != null) {
                    return cached;
                }

                // 2. Fetch live pricing from CSP API
                PricingSnapshotDto liveSnapshot = client.fetchLivePricing(request);
                if (liveSnapshot != null) {
                    priceCacheService.cachePrice(cacheKey, liveSnapshot);
                }
                return liveSnapshot;
            }, executorService);

            futures.add(future);
        }

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        try {
            allFutures.get(); // Wait for all 4 CSP calls to complete concurrently
        } catch (Exception e) {
            // Log concurrency exception
        }

        List<PricingSnapshotDto> results = new ArrayList<>();
        for (CompletableFuture<PricingSnapshotDto> future : futures) {
            try {
                PricingSnapshotDto snapshot = future.get();
                if (snapshot != null) {
                    results.add(snapshot);
                }
            } catch (Exception e) {
                // Ignore individual provider timeout/error
            }
        }

        return results;
    }
}
