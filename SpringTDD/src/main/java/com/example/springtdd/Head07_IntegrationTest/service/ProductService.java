package com.example.springtdd.Head07_IntegrationTest.service;

import com.example.springtdd.Head07_IntegrationTest.domain.product.Product;
import com.example.springtdd.Head07_IntegrationTest.dto.CreateProductRequest;
import com.example.springtdd.Head07_IntegrationTest.dto.ProductResponse;
import com.example.springtdd.Head07_IntegrationTest.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .category(request.getCategory())
                .stock(request.getStock())
                .build();

        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow();

        // 일부러 지연 → 캐시 성능 차이 확인
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}