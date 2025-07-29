package org.example.oopProgramming.topic03_OOP4principles.example03;

import org.example.oopProgramming.topic03_OOP4principles.example01.Payment;

public class OrderService {
    public void processPayment(Payment method, double amount) {
        method.pay(amount);
    }
}
