package org.example.oopProgramming.topic04_SOLID.example02_OCP;

public class PaymentProcessorTest {
    public static void main(String[] args) {
        PaymentProcessor paymentProcessor = new PaymentProcessor();
        Payment[] payments = new Payment[]{
                new AccountTransferPayment(),
                new CreditCardPayment(),
                new MobilePayment()
        };
        double[] paymentAmounts = new double[]{
                500,2000,1050
        };
        int index = 0;
        for (Payment payment : payments) {
            paymentProcessor.pay(payment, paymentAmounts[index++]);
        }
    }
}
