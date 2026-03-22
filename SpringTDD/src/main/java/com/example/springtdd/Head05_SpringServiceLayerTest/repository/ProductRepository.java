package com.example.springtdd.Head05_SpringServiceLayerTest.repository;

import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);
}

