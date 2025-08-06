package org.example.Head02_javaProgrammingStart.example05_ConditionAndIterable03;

public class LoopExample {
    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            System.out.println("i = " + i);
        }

        int count = 0;
        while (count < 3) {
            System.out.println("count = " + count);
            count++;
        }
    }
}
