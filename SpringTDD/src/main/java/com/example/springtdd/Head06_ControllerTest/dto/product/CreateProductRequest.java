package com.example.springtdd.Head06_ControllerTest.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateProductRequest {

    private String name;
    private int price;
    private String category;
}
