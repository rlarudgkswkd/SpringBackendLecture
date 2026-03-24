package com.example.springtdd.Head07_IntegrationTest.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class CreateProductRequest {
    private String name;
    private int price;
    private String category;
    private int stock;
}
