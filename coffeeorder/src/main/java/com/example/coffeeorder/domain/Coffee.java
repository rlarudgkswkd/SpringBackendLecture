package com.example.coffeeorder.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "coffees")
public class Coffee {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    private int price;

    protected Coffee() {}

}