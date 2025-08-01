package org.example.Head04_Collections.topic03_StreamAPI.example06_map;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class MapNamesExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        Function<String, Integer> nameLength = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        };

        names.stream()
                .map(nameLength)
                .forEach(length -> System.out.println("Name length: " + length));
    }
}
