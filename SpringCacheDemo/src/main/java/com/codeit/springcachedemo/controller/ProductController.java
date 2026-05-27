package com.codeit.springcachedemo.controller;

import com.codeit.springcachedemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/products")
    public Map<String, Object> getProduct(@RequestParam Long id) {

        long start = System.currentTimeMillis();

        String result = productService.getProduct(id);

        long end = System.currentTimeMillis();

        return Map.of(
                "result", result,
                "duration(ms)", end - start
        );
    }

    @GetMapping("/api/cache/manager")
    public Map<String, Object> cacheManager() {

        productService.printCacheManagerType();

        return Map.of("success", true);
    }

    @GetMapping("/api/cache/stats")
    public Map<String, Object> cacheStats() {

        productService.printCacheStats();

        return Map.of("success", true);
    }

    @DeleteMapping("/api/cache/products")
    public Map<String, Object> evictProduct(@RequestParam Long id) {

        productService.evictProduct(id);

        return Map.of(
                "success", true,
                "evictedId", id
        );
    }

    @DeleteMapping("/api/cache/products/all")
    public Map<String, Object> evictAllProducts() {

        productService.evictAllProducts();

        return Map.of("success", true);
    }
}