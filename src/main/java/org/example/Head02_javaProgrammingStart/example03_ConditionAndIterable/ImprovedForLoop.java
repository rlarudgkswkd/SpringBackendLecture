package org.example.Head02_javaProgrammingStart.example03_ConditionAndIterable;

public class ImprovedForLoop {
    public static void main(String[] args) {
        int[] scores = {90, 85, 78};

        for (int s : scores) {
            System.out.println("점수: " + s);
        }
    }
}
