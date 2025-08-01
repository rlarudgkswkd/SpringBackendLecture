package org.example.Head04_Collections.topic04_LambdaExpression.example11_Practice;

import java.util.Arrays;
import java.util.List;

public class LambdaPracticeRefactored {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        // 1. Consumer - 출력 (람다 → 메서드 참조)
        names.forEach(System.out::println);

        // 2. Predicate - 이름 길이 5 이상 필터링 (람다 사용)
        names.stream()
                .filter(s -> s.length() >= 5)
                .forEach(System.out::println);

        // 3. Function - 대문자로 변환 (람다 → 메서드 참조)
        names.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}