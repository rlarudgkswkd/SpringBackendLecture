package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example01;

import java.util.Arrays;
import java.util.List;

public class DistinctExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Kim", "Lee", "Kim", "Park", "Lee");

        names.stream()
                .distinct()
                .forEach(name -> System.out.println("Distinct name: " + name));
    }
}
