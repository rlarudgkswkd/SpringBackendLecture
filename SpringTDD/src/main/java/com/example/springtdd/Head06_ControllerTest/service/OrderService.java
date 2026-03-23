package com.example.springtdd.Head06_ControllerTest.service;

import com.example.springtdd.Head06_ControllerTest.dto.order.CreateOrderRequest;

public interface OrderService {
    void createOrder(CreateOrderRequest request);
}
