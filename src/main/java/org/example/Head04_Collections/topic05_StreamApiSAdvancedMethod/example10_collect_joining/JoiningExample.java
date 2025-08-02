package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example10_collect_joining;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JoiningExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        String result = names.stream()
                .collect(Collectors.joining(", ")); // 구분자 쉼표

        System.out.println("Formatted result: " + result);
    }
}
