package org.example.Head03_oopProgramming.topic03_OOP4principles.example01;

public class Order {
    private Payment payment;

    public Order(Payment payment) {
        this.payment = payment;
    }

    public void process(double amount) {
        payment.pay(amount);
    }
}
