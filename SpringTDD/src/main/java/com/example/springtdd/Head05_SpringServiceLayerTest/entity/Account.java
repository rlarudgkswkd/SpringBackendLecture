package com.example.springtdd.Head05_SpringServiceLayerTest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    public Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
        this.status = AccountStatus.ACTIVE;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
