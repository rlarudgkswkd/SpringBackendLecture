package com.example.coffeeorder.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 실무 권장 LAZY
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderCoffee> orderCoffees = new ArrayList<>();

    protected Order() {}

    public Order(Member member) {
        this.member = member;
    }

    public Long getId() { return id; }
    public Member getMember() { return member; }
    public List<OrderCoffee> getOrderCoffees() { return orderCoffees; }

    public void addOrderCoffee(OrderCoffee oc) {
        this.orderCoffees.add(oc);
        oc.setOrder(this);
    }
}
