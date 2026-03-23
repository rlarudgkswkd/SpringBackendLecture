package com.example.springtdd.Head06_ControllerTest.controller;


import com.example.springtdd.Head06_ControllerTest.dto.CreateProductRequest;
import com.example.springtdd.Head06_ControllerTest.dto.PageResponse;
import com.example.springtdd.Head06_ControllerTest.dto.ProductResponse;
import com.example.springtdd.Head06_ControllerTest.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public PageResponse<ProductResponse> getProducts(
            Pageable pageable,
            @RequestParam String category
    ) {
        return productService.getProducts(pageable, category);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestPart("product") CreateProductRequest request,
            @RequestPart("image") MultipartFile image
    ) {

        ProductResponse response = productService.createProduct(request, image);

        return ResponseEntity
                .created(URI.create("/api/products/" + response.getId()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {

        ProductResponse response = productService.getProductById(id);

        return ResponseEntity.ok(response);
    }
}
