package com.example.springtdd.Head07_IntegrationTest.domain.order;

import com.example.springtdd.Head07_IntegrationTest.domain.user.User2;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order2 {

    @Id @GeneratedValue
    private Long id;

    private int totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private User2 user;

    public Order2(User2 user, int totalAmount) {
        this.user = user;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.CONFIRMED;
    }
}
