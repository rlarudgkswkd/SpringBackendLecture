package org.example.Head04_Collections.topic03_StreamAPI.example07_MapEntryTransformation;

import java.util.HashMap;
import java.util.Map;

public class MapEntryTransformationExampleLambda {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 92);

        scores.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .forEach(result -> System.out.println("Student Info: " + result));
    }
}
