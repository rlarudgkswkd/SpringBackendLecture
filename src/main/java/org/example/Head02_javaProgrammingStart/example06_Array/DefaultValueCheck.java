package org.example.Head02_javaProgrammingStart.example06_Array;

public class DefaultValueCheck {
    public static void main(String[] args) {
        boolean[] flags = new boolean[2];
        int[] numbers = new int[2];
        String[] strings = new String[2];
        System.out.println("flags[0] : " + flags[0]); // false
        System.out.println("numbers[0] : " + numbers[0]); // 0
        System.out.println("strings[0] : " + strings[0]); // null
    }
}
