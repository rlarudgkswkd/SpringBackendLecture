package org.example.Head05_AlgoAndDataStructure.topic02_dataStructureSummary.example07_hashMap;

import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("apple", 1000);
        map.put("banana", 1500);
        map.put("apple", 1200); // 값 덮어쓰기

        System.out.println(map.get("apple"));         // 1200
        System.out.println(map.containsKey("banana")); // true

    }
}
