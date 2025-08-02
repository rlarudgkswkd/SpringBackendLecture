package org.example.Head04_Collections.topic06_dataAnalysisAndReport.example06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MinAndMaxByExample {
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");

        Optional<String> minFruit = fruits.stream()
                .collect(Collectors.minBy(Comparator.naturalOrder()));
        Optional<String> maxFruit = fruits.stream()
                .collect(Collectors.maxBy(Comparator.naturalOrder()));

        System.out.println("Lexicographically smallest fruit: " + minFruit.orElse("None"));
        System.out.println("Lexicographically biggest fruit: " + maxFruit.orElse("None"));
    }
}
