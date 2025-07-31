package org.example.Head03_oopProgramming.topic04_SOLID.example02_OCP;

public class AccountTransferPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("계좌이체 결제: " + amount + "원");
    }
}
