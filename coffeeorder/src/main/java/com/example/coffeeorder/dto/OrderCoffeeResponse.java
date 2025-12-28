package com.example.coffeeorder.dto;

public record OrderCoffeeResponse(
        Long coffeeId,
        String coffeeName,
        int price,
        int quantity
) {}
