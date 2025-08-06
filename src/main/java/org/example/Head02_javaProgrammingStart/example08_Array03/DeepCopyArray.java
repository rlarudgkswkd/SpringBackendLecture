package org.example.Head02_javaProgrammingStart.example08_Array03;

public class DeepCopyArray {
    public static void main(String[] args) {
        int[] original = {1, 2, 3};
        int[] copy = new int[original.length];

        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }

        copy[0] = 100;
        System.out.println(original[0]); // 1 (영향 없음)
    }
}