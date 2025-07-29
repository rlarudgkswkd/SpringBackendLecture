package org.example.oopProgramming.topic04_SOLID.example02_OCP;

public class MobilePayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("모바일 결제: " + amount + "원");
    }
}
