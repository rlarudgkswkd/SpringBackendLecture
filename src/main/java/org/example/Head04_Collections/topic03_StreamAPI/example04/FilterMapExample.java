package org.example.Head04_Collections.topic03_StreamAPI.example04;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FilterMapExample {
    public static void main(String[] args) {
        // 학생별 점수를 저장한 해시맵
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Kim", 90);
        scores.put("Lee", 75);
        scores.put("Park", 88);

        // 익명 클래스를 사용한 Predicate (점수가 80 이상인지 판별)
        Predicate<Map.Entry<String, Integer>> highScore = new Predicate<Map.Entry<String, Integer>>() {
            @Override
            public boolean test(Map.Entry<String, Integer> entry) {
                return entry.getValue() >= 80;
            }
        };

        scores.entrySet().stream()
                .filter(highScore)
                .forEach(entry ->
                        System.out.println("High scorer: " + entry.getKey() + " => " + entry.getValue()));
    }
}
