package com.example.springtdd.Head07_IntegrationTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemRequest {
    private Long productId;
    private int quantity;
    private int price;
}
