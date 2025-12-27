package org.example.springdatajpaexample.dto;

public record MenuResponse(
        Long id,
        String name,
        int price,
        String categoryName
) {}