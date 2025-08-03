package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example09_MapToCsv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapToCsvExample {
    public static void main(String[] args) {
        Map<String, Integer> stockMap = new HashMap<>();
        stockMap.put("apple", 10);
        stockMap.put("banana", 20);
        stockMap.put("cherry", 30);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("stock.csv"))) {
            // 헤더
            bw.write("key,value");
            bw.newLine();

            for (Map.Entry<String, Integer> entry : stockMap.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                bw.write(key + "," + value);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
