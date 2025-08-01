package org.example.Head04_Collections.topic04_LambdaExpression.example08_Function;

import java.util.function.Function;

public class FunctionExample {
    public static void main(String[] args) {
        Function<String, Integer> lengthFunc = str -> str.length();
        System.out.println(lengthFunc.apply("Hello")); // 5
    }
}
