package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example03;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedCustomExample {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("Seoul", "Busan", "New York", "London");

        // 길이를 기준으로 오름차순 정렬
        cities.stream()
                .sorted(Comparator.comparingInt(String::length))
                .forEach(city -> System.out.println("Sorted city: " + city));
    }
}
