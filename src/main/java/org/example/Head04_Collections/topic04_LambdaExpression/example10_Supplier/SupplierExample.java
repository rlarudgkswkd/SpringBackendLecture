package org.example.Head04_Collections.topic04_LambdaExpression.example10_Supplier;

import java.util.Random;
import java.util.function.Supplier;

public class SupplierExample {
    public static void main(String[] args) {
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(100);
        System.out.println("Random number: " + randomSupplier.get());
        System.out.println("Another random: " + randomSupplier.get());
    }
}
