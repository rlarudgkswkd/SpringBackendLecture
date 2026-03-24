package com.example.springtdd.Head07_IntegrationTest.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateOrderRequest {

    private Long userId;
    private List<OrderItemRequest> items;

    private DeliveryAddress deliveryAddress;
    private PaymentMethod paymentMethod;
}