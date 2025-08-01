package org.example.Head04_Collections.topic03_StreamAPI.example07_MapEntryTransformation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapEntryTransformationExample {
    public static void main(String[] args) {
        // 학생별 점수 정보
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 92);

        // Map.Entry<String, Integer> -> 문자열 변환
        Function<Map.Entry<String, Integer>, String> entryToString =
                new Function<Map.Entry<String, Integer>, String>() {
                    @Override
                    public String apply(Map.Entry<String, Integer> e) {
                        return e.getKey() + ": " + e.getValue();
                    }
                };

        scores.entrySet().stream()
                .map(entryToString)
                .forEach(result -> System.out.println("Student Info: " + result));
    }
}
