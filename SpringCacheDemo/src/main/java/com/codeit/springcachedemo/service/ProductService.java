package com.codeit.springcachedemo.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.*;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@CacheConfig(cacheNames = "products")
public class ProductService {

    private final CacheManager cacheManager;

    public ProductService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Cacheable(
            key = "#id",
            condition = "#id > 0",
            unless = "#result.contains('미판매')"
    )
    public String getProduct(Long id) {

        printLog("상품 조회 시작");
        printLog("id = " + id);

        sleep(3000);

        if (id == 999L) {

            return "미판매상품";
        }

        printLog("DB 조회 완료");

        return "상품-" + id;
    }

    @CachePut(key = "#id")
    public String updateProduct(
            Long id,
            String name
    ) {

        printLog("상품 수정");
        printLog("캐시 강제 갱신");

        sleep(2000);

        return name;
    }

    @CacheEvict(
            key = "#id"
    )
    public void evictProduct(Long id) {

        printLog("특정 캐시 삭제");
        printLog("삭제 key = " + id);
    }

    @CacheEvict(
            allEntries = true,
            beforeInvocation = true
    )
    public void evictAllProducts() {

        printLog("전체 캐시 삭제");
        printLog("beforeInvocation = true");
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

    @Cacheable(
            value = "productsByMethod",
            key = "#root.methodName + '_' + #id"
    )
    public String getProductByMethod(Long id) {

        printLog("메서드명 기반 캐시 키");

        sleep(2000);

        return "method-product-" + id;
    }

    @Cacheable(
            value = "productsByClass",
            key = "#root.targetClass.simpleName + '_' + #id"
    )
    public String getProductByClass(Long id) {

        printLog("클래스명 기반 캐시 키");

        sleep(2000);

        return "class-product-" + id;
    }

    @Cacheable(
            value = "productsByCustomGenerator",
            keyGenerator = "customKeyGenerator"
    )
    public String getProductByGenerator(Long id) {

        printLog("커스텀 KeyGenerator");

        sleep(2000);

        return "generator-product-" + id;
    }

    @Caching(
            put = {
                    @CachePut(value = "products", key = "#id"),
                    @CachePut(value = "productNames", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "searchResults", allEntries = true),
                    @CacheEvict(value = "popularProducts", allEntries = true)
            }
    )
    public String updateComplexCache(
            Long id,
            String name
    ) {

        printLog("@Caching 복합 작업");

        sleep(2000);

        return name;
    }

    public void printCacheNames() {

        System.out.println();
        System.out.println("========== 등록된 캐시 및 Key 조회 ==========");

        cacheManager.getCacheNames()
                .forEach(cacheName -> {

                    System.out.println();
                    System.out.println("cacheName: " + cacheName);

                    Cache cache = cacheManager.getCache(cacheName);

                    // ConcurrentMapCache 로 다운캐스팅
                    if (cache instanceof ConcurrentMapCache concurrentMapCache) {

                        System.out.println("keys:");

                        concurrentMapCache
                                .getNativeCache()
                                .keySet()
                                .forEach(key ->
                                        System.out.println(" - " + key)
                                );
                    }
                });

        System.out.println("===========================================");
        System.out.println();
    }
}