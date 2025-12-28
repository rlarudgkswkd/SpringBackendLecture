package com.example.coffeeorder.dto;

import java.util.List;

public record OrderCreateRequest(
        Long memberId,
        List<OrderCoffeeRequest> orderCoffees
) {}