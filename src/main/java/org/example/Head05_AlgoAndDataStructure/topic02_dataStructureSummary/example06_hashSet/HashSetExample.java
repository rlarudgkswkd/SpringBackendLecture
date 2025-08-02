package org.example.Head05_AlgoAndDataStructure.topic02_dataStructureSummary.example06_hashSet;

import java.util.HashSet;
import java.util.Set;

public class HashSetExample {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("apple");
        set.add("banana");
        set.add("apple"); // 중복 무시

        System.out.println(set.contains("banana")); // true
        System.out.println(set.size());              // 2
    }
}
