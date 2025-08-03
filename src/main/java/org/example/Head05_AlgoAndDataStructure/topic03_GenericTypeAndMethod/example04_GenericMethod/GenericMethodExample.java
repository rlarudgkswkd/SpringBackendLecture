package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example04_GenericMethod;

public class GenericMethodExample {
    public static <U extends Comparable<U>> U max(U a, U b) {
        if (a.compareTo(b) >= 0) {
            return a;
        } else {
            return b;
        }
    }

    public static void main(String[] args) {
        System.out.println(max(10, 20));         // 20 (Integer)
        System.out.println(max("apple", "pear")); // pear (String)
    }
}
