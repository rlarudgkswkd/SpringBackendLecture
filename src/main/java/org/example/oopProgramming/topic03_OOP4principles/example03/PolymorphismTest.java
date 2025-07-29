package org.example.oopProgramming.topic03_OOP4principles.example03;

import org.example.oopProgramming.topic03_OOP4principles.example01.AccountTransferPayment;
import org.example.oopProgramming.topic03_OOP4principles.example01.CreditCardPayment;
import org.example.oopProgramming.topic03_OOP4principles.example01.CryptoPayment;
import org.example.oopProgramming.topic03_OOP4principles.example01.Payment;

public class PolymorphismTest {
    public static void main(String[] args) {
        Payment[] payments = new Payment[]
                {
                    new CreditCardPayment(),
                    new AccountTransferPayment(),
                    new CryptoPayment()
                };
        double[] paymentAmounts = new double[] {
                30000,
                70000,
                100000
        };
        OrderService service = new OrderService();
        for (Payment payment : payments) {
            if(payment instanceof CreditCardPayment) {
                service.processPayment(payment, paymentAmounts[0]);
            } else if(payment instanceof AccountTransferPayment) {
                service.processPayment(payment, paymentAmounts[1]);
            } else if(payment instanceof CryptoPayment) {
                service.processPayment(payment, paymentAmounts[2]);
            } else {
                service.processPayment(payment, 1000);
            }
        }
//        service.processPayment(new CreditCardPayment(), 10000);
//        service.processPayment(new AccountTransferPayment(), 20000);
    }
}