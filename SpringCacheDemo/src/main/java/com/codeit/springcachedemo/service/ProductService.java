package com.codeit.springcachedemo.service;

import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ProductService {

    private final CacheManager cacheManager;

    public ProductService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = "products", key = "#id")
    public String getProduct(Long id) {

        printLog("상품 조회 시작");
        printLog("캐시 미스 발생 → 실제 메서드 실행");
        printLog("id = " + id);

        sleep(3000);

        printLog("DB 조회 완료");

        return "상품-" + id;
    }

    @CacheEvict(value = "products", key = "#id")
    public void evictProduct(Long id) {

        printLog("명시적 캐시 제거 요청");
        printLog("id = " + id);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void evictAllProducts() {

        printLog("전체 캐시 제거 요청");
    }

    public void printCacheManagerType() {

        System.out.println();
        System.out.println("========== CacheManager 구현체 ==========");
        System.out.println(cacheManager.getClass().getName());
        System.out.println("=======================================");
        System.out.println();
    }

    public void printCacheStats() {

        Cache cache = cacheManager.getCache("products");

        if (!(cache instanceof CaffeineCache caffeineCache)) {
            System.out.println("CaffeineCache가 아닙니다.");
            return;
        }

        com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                caffeineCache.getNativeCache();

        CacheStats stats = nativeCache.stats();

        System.out.println();
        System.out.println("========== products 캐시 통계 ==========");
        System.out.println("estimatedSize = " + nativeCache.estimatedSize());
        System.out.println("hitCount = " + stats.hitCount());
        System.out.println("missCount = " + stats.missCount());
        System.out.println("hitRate = " + stats.hitRate());
        System.out.println("evictionCount = " + stats.evictionCount());
        System.out.println("======================================");
        System.out.println();
    }

    private void sleep(long millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void printLog(String message) {

        System.out.println(
                "[" + LocalTime.now().withNano(0)
                        + "] [ProductService] "
                        + message
        );
    }
}