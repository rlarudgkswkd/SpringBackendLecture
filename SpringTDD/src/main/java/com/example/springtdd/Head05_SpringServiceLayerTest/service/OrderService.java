package com.example.springtdd.Head05_SpringServiceLayerTest.service;

import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Member;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Order;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Product;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.DiscountPolicy;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final DiscountPolicy discountPolicy;

    public Order createOrder(Member member, Long productId, int quantity) {

        Product product = productRepository.findById(productId).orElseThrow();

        int totalPrice = product.getPrice() * quantity;
        int discount = discountPolicy.calculateDiscount(member, totalPrice);

        return new Order(totalPrice - discount, discount);
    }
}
