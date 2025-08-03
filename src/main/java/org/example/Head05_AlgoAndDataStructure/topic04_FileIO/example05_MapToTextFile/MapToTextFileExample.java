package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example05_MapToTextFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapToTextFileExample {
    public static void main(String[] args) {
        Map<String, Integer> stockMap = new HashMap<>();
        stockMap.put("apple", 10);
        stockMap.put("banana", 20);
        stockMap.put("cherry", 30);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("stock.txt"))) {
            // 간단히 "key value" 형태로 한 줄씩 출력
            for (Map.Entry<String, Integer> entry : stockMap.entrySet()) {
                bw.write(entry.getKey() + " " + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
