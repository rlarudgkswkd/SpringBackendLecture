package org.example.Head02_javaProgrammingStart.example07_Array02;

public class OutOfBounds {
    public static void main(String[] args) {
        int[] data = {1, 2, 3};
        System.out.println(data[3]); // 오류: ArrayIndexOutOfBoundsException
    }
}