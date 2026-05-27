package com.codeit.springcachedemo.indicator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheHealthIndicator
        implements HealthIndicator {

    private final CacheManager cacheManager;

    public CacheHealthIndicator(
            CacheManager cacheManager
    ) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Health health() {

        try {

            Cache cache =
                    cacheManager.getCache("products");

            cache.put(
                    "health-check",
                    "ok"
            );

            String value =
                    cache.get(
                            "health-check",
                            String.class
                    );

            return Health.up()
                    .withDetail(
                            "cache",
                            "available"
                    )
                    .withDetail(
                            "sample-value",
                            value
                    )
                    .build();

        } catch (Exception e) {

            return Health.down()
                    .withDetail(
                            "cache",
                            "unavailable"
                    )
                    .withDetail(
                            "error",
                            e.getMessage()
                    )
                    .build();
        }
    }
}