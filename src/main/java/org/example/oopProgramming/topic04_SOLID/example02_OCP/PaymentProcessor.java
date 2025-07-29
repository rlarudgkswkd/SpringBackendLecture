package org.example.oopProgramming.topic04_SOLID.example02_OCP;

public class PaymentProcessor {
    public void pay(Payment payment, double amount) {
        payment.pay(amount);
    }
}
