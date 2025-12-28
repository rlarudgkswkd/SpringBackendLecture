package com.example.coffeeorder.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "order_coffees")
public class OrderCoffee {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coffee_id", nullable = false)
    private Coffee coffee;

    @Column(nullable = false)
    private int quantity;

    protected OrderCoffee() {}

    public OrderCoffee(Coffee coffee, int quantity) {
        this.coffee = coffee;
        this.quantity = quantity;
    }

    public Coffee getCoffee() { return coffee; }
    public int getQuantity() { return quantity; }

    void setOrder(Order order) {
        this.order = order;
    }
}
