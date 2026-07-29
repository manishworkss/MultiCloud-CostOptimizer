package com.cloud.optimizer;

import com.cloud.optimizer.client.*;
import com.cloud.optimizer.dto.PricingSnapshotDto;
import com.cloud.optimizer.model.DeploymentRequest;
import com.cloud.optimizer.service.PriceCacheService;
import com.cloud.optimizer.service.PricingAggregationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PricingAggregationServiceTest {

    private PricingAggregationService aggregationService;

    @BeforeEach
    void setUp() {
        @SuppressWarnings("unchecked")
        RedisTemplate<String, Object> redisTemplate = Mockito.mock(RedisTemplate.class);
        PriceCacheService cacheService = new PriceCacheService(redisTemplate);

        List<CloudPricingClient> clients = List.of(
                new AwsPricingClient(),
                new AzurePricingClient(),
                new GcpPricingClient(),
                new OciPricingClient()
        );

        aggregationService = new PricingAggregationService(clients, cacheService);
    }

    @Test
    void testFetchAllProviderPricing() {
        DeploymentRequest request = DeploymentRequest.builder()
                .cpu("4 Cores")
                .ram("16 GB")
                .storage("200 GB SSD")
                .operatingSystem("Ubuntu Linux 22.04")
                .databaseType("PostgreSQL")
                .bandwidth("500 GB")
                .region("US-East (N. Virginia)")
                .expectedUsers(10000)
                .build();

        List<PricingSnapshotDto> snapshots = aggregationService.fetchAllProviderPricing(request);

        assertNotNull(snapshots);
        assertEquals(4, snapshots.size());

        for (PricingSnapshotDto snapshot : snapshots) {
            assertNotNull(snapshot.getProviderId());
            assertNotNull(snapshot.getTotalMonthlyCost());
            assertTrue(snapshot.getTotalMonthlyCost().doubleValue() > 0);
        }
    }
}
