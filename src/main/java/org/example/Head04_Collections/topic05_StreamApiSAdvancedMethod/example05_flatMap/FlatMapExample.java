package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example05_flatMap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapExample {
    public static void main(String[] args) {
        List<List<String>> nestedList = Arrays.asList(
                Arrays.asList("Apple", "Banana"),
                Arrays.asList("Cat", "Dog"),
                Arrays.asList("Egg")
        );

        // flatMap()으로 1차원 스트림으로 변환
        List<String> flattened = nestedList.stream()
                .flatMap(list -> list.stream())
                .collect(Collectors.toList());

        System.out.println("Flattened list: " + flattened);
    }
}
