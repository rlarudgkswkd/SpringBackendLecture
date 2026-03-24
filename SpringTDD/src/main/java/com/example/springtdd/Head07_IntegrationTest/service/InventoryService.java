package com.example.springtdd.Head07_IntegrationTest.service;

import com.example.springtdd.Head07_IntegrationTest.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    public InventoryService(ProductRepository productRepository) {
    }
    // 지금은 테스트용이라 비워둬도 됨
}