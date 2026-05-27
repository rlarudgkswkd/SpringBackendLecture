package com.codeit.springcachedemo.controller;

import com.codeit.springcachedemo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/products")
    public Map<String, Object> getProduct(
            @RequestParam Long id
    ) {

        long start = System.currentTimeMillis();

        String result =
                productService.getProduct(id);

        long end = System.currentTimeMillis();

        return Map.of(
                "result", result,
                "duration(ms)", end - start
        );
    }

    @GetMapping("/api/products/update")
    public Map<String, Object> updateProduct(
            @RequestParam Long id,
            @RequestParam String name
    ) {

        String result =
                productService.updateProduct(
                        id,
                        name
                );

        return Map.of(
                "result", result
        );
    }

    @GetMapping("/api/cache/evict")
    public Map<String, Object> evictProduct(
            @RequestParam Long id
    ) {

        productService.evictProduct(id);

        return Map.of(
                "deletedKey", id
        );
    }

    @GetMapping("/api/cache/evict-all")
    public Map<String, Object> evictAllProducts() {

        productService.evictAllProducts();

        return Map.of(
                "success", true
        );
    }

    private void printLog(String message) {

        System.out.println(
                "[" + LocalTime.now().withNano(0)
                        + "] [ProductController] "
                        + message
        );
    }

    @GetMapping("/api/products/method")
    public Map<String, Object> getMethodProduct(
            @RequestParam Long id
    ) {

        return Map.of(
                "result",
                productService.getProductByMethod(id)
        );
    }

    @GetMapping("/api/products/class")
    public Map<String, Object> getClassProduct(
            @RequestParam Long id
    ) {

        return Map.of(
                "result",
                productService.getProductByClass(id)
        );
    }

    @GetMapping("/api/products/generator")
    public Map<String, Object> getGeneratorProduct(
            @RequestParam Long id
    ) {

        return Map.of(
                "result",
                productService.getProductByGenerator(id)
        );
    }

    @GetMapping("/api/products/complex-update")
    public Map<String, Object> updateComplexCache(
            @RequestParam Long id,
            @RequestParam String name
    ) {

        return Map.of(
                "result",
                productService.updateComplexCache(
                        id,
                        name
                )
        );
    }

    @GetMapping("/api/cache/names")
    public Map<String, Object> cacheNames() {

        productService.printCacheNames();

        return Map.of(
                "success", true
        );
    }
}