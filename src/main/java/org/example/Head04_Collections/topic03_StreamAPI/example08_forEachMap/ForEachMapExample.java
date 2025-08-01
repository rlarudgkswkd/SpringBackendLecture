package org.example.Head04_Collections.topic03_StreamAPI.example08_forEachMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ForEachMapExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Kim", 90);
        scores.put("Lee", 85);
        scores.put("Park", 92);

        Consumer<Map.Entry<String, Integer>> printEntry =
                new Consumer<Map.Entry<String, Integer>>() {
                    @Override
                    public void accept(Map.Entry<String, Integer> e) {
                        System.out.println("Key: " + e.getKey() + ", Value: " + e.getValue());
                    }
                };

        scores.entrySet().stream().forEach(printEntry);

        System.out.println(scores);
    }
}
