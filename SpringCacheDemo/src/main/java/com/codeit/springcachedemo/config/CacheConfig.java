package com.codeit.springcachedemo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalTime;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {

        CaffeineCacheManager cacheManager =
                new CaffeineCacheManager();

        cacheManager.registerCustomCache(
                "products",
                Caffeine.newBuilder()
                        .maximumSize(3)
                        .expireAfterWrite(Duration.ofSeconds(10))
                        .recordStats()
                        .removalListener((Object key, Object value, RemovalCause cause) -> {
                            System.out.println(
                                    "[" + LocalTime.now().withNano(0)
                                            + "] [CacheRemovalListener] "
                                            + "key=" + key
                                            + ", value=" + value
                                            + ", cause=" + cause
                            );
                        })
                        .build()
        );

        return cacheManager;
    }
}