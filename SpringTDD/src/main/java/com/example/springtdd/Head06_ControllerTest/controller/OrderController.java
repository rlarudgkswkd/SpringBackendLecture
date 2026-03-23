package com.example.springtdd.Head06_ControllerTest.controller;

import com.example.springtdd.Head06_ControllerTest.dto.order.CreateOrderRequest;
import com.example.springtdd.Head06_ControllerTest.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void createOrder(@Valid @RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
    }
}
