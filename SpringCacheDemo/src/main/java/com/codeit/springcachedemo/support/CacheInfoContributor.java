package com.codeit.springcachedemo.support;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CacheInfoContributor {

    @Bean
    public InfoContributor cacheInfo(
            CacheManager cacheManager
    ) {

        return builder -> {

            Map<String, Object> cacheInfo =
                    new HashMap<>();

            cacheManager.getCacheNames()
                    .forEach(cacheName -> {

                        Cache cache =
                                cacheManager.getCache(cacheName);

                        if (cache instanceof CaffeineCache caffeineCache) {

                            com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                                    caffeineCache.getNativeCache();

                            CacheStats stats =
                                    nativeCache.stats();

                            Map<String, Object> detail =
                                    new HashMap<>();

                            detail.put(
                                    "size",
                                    nativeCache.estimatedSize()
                            );

                            detail.put(
                                    "hitRate",
                                    stats.hitRate()
                            );

                            detail.put(
                                    "missRate",
                                    stats.missRate()
                            );

                            detail.put(
                                    "evictionCount",
                                    stats.evictionCount()
                            );

                            cacheInfo.put(
                                    cacheName,
                                    detail
                            );
                        }
                    });

            builder.withDetail(
                    "caches",
                    cacheInfo
            );
        };
    }
}