package com.example.springtdd.Head07_IntegrationTest.domain.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int stock;
    private String category;

    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("재고 부족");
        }
        this.stock -= quantity;
    }
}
