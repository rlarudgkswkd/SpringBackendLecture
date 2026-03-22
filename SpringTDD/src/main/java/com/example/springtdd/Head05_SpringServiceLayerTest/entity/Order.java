package com.example.springtdd.Head05_SpringServiceLayerTest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Order {
    private int finalAmount;
    private int discountAmount;
}
