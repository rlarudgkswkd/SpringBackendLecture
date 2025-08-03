package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example06_TextFileToMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextFIleToMapExample {
    public static void main(String[] args) {
        Map<String, Integer> loadedMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("stock.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // "key value" 구조로 split
                String[] tokens = line.split(" ");
                String key = tokens[0];
                int value = Integer.parseInt(tokens[1]);
                loadedMap.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("로드된 맵: " + loadedMap);

    }
}
