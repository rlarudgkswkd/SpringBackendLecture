package org.example.Head04_Collections.topic04_LambdaExpression.example07_Predicate;

import java.util.function.Predicate;

public class PredicateExample {
    public static void main(String[] args) {
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println(isEven.test(10)); // true
        System.out.println(isEven.test(11)); // false
    }
}
