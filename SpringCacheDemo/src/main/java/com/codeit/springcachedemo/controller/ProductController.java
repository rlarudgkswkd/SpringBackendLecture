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

        printLog("상품 조회 요청");

        long start = System.currentTimeMillis();

        String result =
                productService.getProduct(id);

        long end = System.currentTimeMillis();

        return Map.of(
                "result", result,
                "duration(ms)", end - start
        );
    }

    @GetMapping("/api/products/custom")
    public Map<String, Object> getCustomProduct(
            @RequestParam Long id
    ) {

        printLog("커스텀 키 조회 요청");

        long start = System.currentTimeMillis();

        String result =
                productService.getProductWithCustomKey(id);

        long end = System.currentTimeMillis();

        return Map.of(
                "result", result,
                "duration(ms)", end - start
        );
    }

    @GetMapping("/api/products/category")
    public Map<String, Object> getCategoryProduct(
            @RequestParam String category,
            @RequestParam Long id
    ) {

        printLog("다중 키 조회 요청");

        long start = System.currentTimeMillis();

        String result =
                productService.getProductWithCategory(
                        category,
                        id
                );

        long end = System.currentTimeMillis();

        return Map.of(
                "result", result,
                "duration(ms)", end - start
        );
    }

    private void printLog(String message) {

        System.out.println(
                "[" + LocalTime.now().withNano(0)
                        + "] [ProductController] "
                        + message
        );
    }
}