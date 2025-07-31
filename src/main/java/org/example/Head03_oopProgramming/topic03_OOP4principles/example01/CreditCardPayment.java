package org.example.Head03_oopProgramming.topic03_OOP4principles.example01;

public class CreditCardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("신용카드로 " + amount + "원 결제 완료.");
    }
}
