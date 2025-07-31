package org.example.Head04_Collections.topic02_JCFCoreInterface.example03;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        // 키-값 쌍 추가
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 95);

        System.out.println("After put: " + scores);

        // 기존 키에 다른 값 할당
        scores.put("Lee", 88);
        System.out.println("After updating 'Lee': " + scores);

        // 특정 키의 값 조회
        int parkScore = scores.get("Park");
        System.out.println("Park's score: " + parkScore);

        // 키 존재 여부
        System.out.println("Contains 'Kim'? " + scores.containsKey("Kim"));

        // 요소 제거
        scores.remove("Kim");
        System.out.println("After remove 'Kim': " + scores);

        // 모든 키 조회
        System.out.println("All keys: " + scores.keySet());
    }
}
