package com.codeit.springgraphqldemo.dto;

public record CreateBookInput(
        String title,
        String author,
        Integer price
) {
}