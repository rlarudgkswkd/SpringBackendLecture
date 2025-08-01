package org.example.Head04_Collections.topic04_LambdaExpression.example11_Practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class LambdaPractice {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // 1. Consumer - 출력
        Consumer<String> print = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        names.forEach(print);

        // 2. Predicate - 이름 길이 5 이상 필터링
        Predicate<String> lengthCheck = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length() >= 5;
            }
        };
        names.stream().filter(lengthCheck).forEach(System.out::println);

        // 3. Function - 이름을 대문자로 변환
        Function<String, String> toUpper = new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s.toUpperCase();
            }
        };
        names.stream().map(toUpper).forEach(System.out::println);
    }
}
