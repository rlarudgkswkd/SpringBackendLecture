package org.example.Head03_oopProgramming.topic04_SOLID.example02_OCP;

public class CreditCardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("신용카드 결제: " + amount + "원");
    }
}
