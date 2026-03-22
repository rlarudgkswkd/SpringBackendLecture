package com.example.springtdd.Head05_SpringServiceLayerTest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderAccountId;
    private Long receiverAccountId;
    private int amount;

    public TransactionHistory(Long senderId, Long receiverId, int amount) {
        this.senderAccountId = senderId;
        this.receiverAccountId = receiverId;
        this.amount = amount;
    }
}
