package com.example.springtdd.Head05_SpringServiceLayerTest.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Product {
    private int price;
    @Setter
    private int stock;

    public Product () {}

    public Product (int price){
        this.price = price;
    }

}
