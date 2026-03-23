package com.example.springtdd.Head06_ControllerTest.dto.order;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderItem {

    @NotNull
    private Long productId;

    @Min(1)
    private int quantity;

    @Min(0)
    private int price;
}
