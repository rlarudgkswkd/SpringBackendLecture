package org.example.Head04_Collections.topic04_LambdaExpression.example09_Consumer;

import java.util.function.Consumer;

public class ConsumerExample {
    public static void main(String[] args) {
        Consumer<String> printer = s -> System.out.println("Consumed: " + s);
        printer.accept("Hello Consumer");

        //Stream API forEach() 동작
    }
}
