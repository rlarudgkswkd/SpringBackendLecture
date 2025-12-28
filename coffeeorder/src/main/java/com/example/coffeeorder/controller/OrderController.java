package com.example.coffeeorder.controller;

import com.example.coffeeorder.dto.OrderCreateRequest;
import com.example.coffeeorder.dto.OrderResponse;
import com.example.coffeeorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse create(@RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping("/{orderId}")
    public OrderResponse get(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping
    public List<OrderResponse> getAll() {
        return orderService.getOrders();
    }
}