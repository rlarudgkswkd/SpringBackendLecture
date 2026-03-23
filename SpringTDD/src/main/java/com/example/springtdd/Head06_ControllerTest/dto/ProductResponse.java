package com.example.springtdd.Head06_ControllerTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String category;
    private String imageUrl; // 추가 필드
}
