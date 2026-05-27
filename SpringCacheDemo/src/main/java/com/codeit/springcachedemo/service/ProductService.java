package com.codeit.springcachedemo.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ProductService {

    private final CacheManager cacheManager;

    public ProductService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Cacheable("products")
    public String getProduct(Long id) {

        printLog("상품 조회 시작");
        printLog("실행 메서드: getProduct()");
        printLog("파라미터 id: " + id);

        sleep(3000);

        printLog("DB 조회 완료");

        return "상품-" + id;
    }

    @Cacheable(
            value = "productsByCustomKey",
            key = "#id"
    )
    public String getProductWithCustomKey(Long id) {

        printLog("커스텀 키 상품 조회");
        printLog("key = #id");

        sleep(3000);

        return "커스텀상품-" + id;
    }

    @Cacheable(
            value = "productsByMultiParam",
            key = "#category + '_' + #id"
    )
    public String getProductWithCategory(
            String category,
            Long id
    ) {

        printLog("다중 파라미터 상품 조회");
        printLog("category: " + category);
        printLog("id: " + id);

        sleep(3000);

        return category + "-상품-" + id;
    }

    public void printCacheNames() {

        System.out.println();
        System.out.println("========== 등록된 캐시 이름 ==========");

        cacheManager.getCacheNames()
                .forEach(cacheName ->
                        System.out.println(
                                "cacheName: " + cacheName
                        )
                );

        System.out.println("====================================");
        System.out.println();
    }

    public void printProductCache(Long id) {

        Cache cache = cacheManager.getCache("products");

        System.out.println();
        System.out.println("========== products 캐시 조회 ==========");

        if (cache == null) {

            System.out.println(
                    "products 캐시가 존재하지 않습니다."
            );

            return;
        }

        Cache.ValueWrapper valueWrapper =
                cache.get(id);

        if (valueWrapper == null) {

            System.out.println(
                    "key = " + id
                            + " 에 대한 캐시가 없습니다."
            );

        } else {

            System.out.println(
                    "key = " + id
            );

            System.out.println(
                    "value = " + valueWrapper.get()
            );
        }

        System.out.println("======================================");
        System.out.println();
    }

    @CacheEvict(
            value = "products",
            key = "#id"
    )
    public void evictProduct(Long id) {

        printLog("캐시 삭제");
        printLog("삭제 key = " + id);
    }

    @CacheEvict(
            value = "products",
            allEntries = true
    )
    public void evictAllProducts() {

        printLog("전체 캐시 삭제");
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