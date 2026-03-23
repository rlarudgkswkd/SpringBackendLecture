package com.example.springtdd.Head06_ControllerTest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderRequest {

    @NotEmpty
    @Valid
    private List<OrderItem> items;

    @Valid
    private DeliveryAddress deliveryAddress;

    @NotEmpty
    private String paymentMethod;
}
