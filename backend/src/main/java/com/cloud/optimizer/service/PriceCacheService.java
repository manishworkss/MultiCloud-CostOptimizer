package com.cloud.optimizer.service;

import com.cloud.optimizer.dto.PricingSnapshotDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PriceCacheService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final long CACHE_TTL_HOURS = 24;

    public PriceCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String buildCacheKey(String providerId, String region, String cpu, String ram) {
        return String.format("price:%s:%s:%s:%s",
                providerId.toLowerCase(),
                region != null ? region.replaceAll("\\s+", "_").toLowerCase() : "default",
                cpu != null ? cpu.replaceAll("\\s+", "").toLowerCase() : "4cores",
                ram != null ? ram.replaceAll("\\s+", "").toLowerCase() : "16gb");
    }

    public PricingSnapshotDto getCachedPrice(String cacheKey) {
        try {
            Object obj = redisTemplate.opsForValue().get(cacheKey);
            if (obj instanceof PricingSnapshotDto) {
                return (PricingSnapshotDto) obj;
            }
        } catch (Exception e) {
            // Gracefully ignore Redis connection failure (falls back to direct API)
        }
        return null;
    }

    public void cachePrice(String cacheKey, PricingSnapshotDto snapshot) {
        try {
            redisTemplate.opsForValue().set(cacheKey, snapshot, CACHE_TTL_HOURS, TimeUnit.HOURS);
        } catch (Exception e) {
            // Gracefully ignore Redis connection failure
        }
    }
}
