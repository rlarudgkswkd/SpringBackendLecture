package com.codeit.springcachedemo.controller;

import com.codeit.springcachedemo.service.CacheMonitoringService;
import com.codeit.springcachedemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.LongStream;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CacheMonitoringService cacheMonitoringService;

    @GetMapping("/api/products")
    public Map<String, Object> getProduct(
            @RequestParam Long id
    ) {

        long start =
                System.currentTimeMillis();

        String result =
                productService.getProduct(id);

        long end =
                System.currentTimeMillis();

        return Map.of(
                "result", result,
                "duration(ms)", end - start
        );
    }

    @GetMapping("/api/cache/report")
    public Map<String, Object> cacheReport() {

        return cacheMonitoringService
                .getCacheReport();
    }

    @GetMapping("/api/cache/pollution-test")
    public Map<String, Object> pollutionTest() {

        LongStream.rangeClosed(1, 20)
                .forEach(productService::getProduct);

        return Map.of(
                "success",
                true
        );
    }
}