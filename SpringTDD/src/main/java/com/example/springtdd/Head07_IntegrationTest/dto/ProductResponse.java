package com.example.springtdd.Head07_IntegrationTest.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
}