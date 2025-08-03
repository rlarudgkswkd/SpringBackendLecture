package org.example.Head05_AlgoAndDataStructure.topic04_FileIO.example03_ListToTextFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListToTextFileExample {
    public static void main(String[] args) {
        List<String> items = new ArrayList<>();
        items.add("Apple");
        items.add("Banana");
        items.add("Cherry");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("items.txt"))) {
            for (String item : items) {
                bw.write(item);
                bw.newLine(); // 줄바꿈
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
