package com.example.coffeeorder.dto;

public record OrderCoffeeRequest(
        Long coffeeId,
        int quantity
) {}