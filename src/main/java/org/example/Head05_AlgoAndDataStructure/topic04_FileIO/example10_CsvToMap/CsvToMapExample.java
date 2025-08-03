package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example10_CsvToMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvToMapExample {
    public static void main(String[] args) {
        Map<String, Integer> loadedMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("stock.csv"))) {
            String line;
            boolean header = true;
            while ((line = br.readLine()) != null) {
                if (header) {
                    header = false; // 첫 줄(헤더) 건너뛰기
                    continue;
                }
                String[] tokens = line.split(",");
                String key = tokens[0];
                int value = Integer.parseInt(tokens[1]);
                loadedMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("CSV로부터 로드된 Map: " + loadedMap);

    }
}
