package com.codeit.springcachedemo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ProductService {

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