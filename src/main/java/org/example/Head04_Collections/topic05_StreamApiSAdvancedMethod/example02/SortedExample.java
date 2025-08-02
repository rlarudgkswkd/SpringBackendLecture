package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example02;

import java.util.Arrays;
import java.util.List;

public class SortedExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5, 1, 8, 2, 9);

        numbers.stream()
                .sorted()    // 오름차순
                .forEach(num -> System.out.print(num + " "));
    }
}

