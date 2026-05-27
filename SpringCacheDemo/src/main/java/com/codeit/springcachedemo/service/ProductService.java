package com.codeit.springcachedemo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class ProductService {

    @Cacheable(value = "products", key = "#id")
    public String getProduct(Long id) {

        printLog("실제 상품 조회 실행");
        printLog("id = " + id);

        sleep(2000);

        return "상품-" + id;
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