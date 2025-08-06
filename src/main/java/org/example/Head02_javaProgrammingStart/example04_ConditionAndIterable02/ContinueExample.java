package org.example.Head02_javaProgrammingStart.example04_ConditionAndIterable02;

public class ContinueExample {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            if (i % 2 == 0) continue;
            System.out.println(i);  // 홀수만 출력
        }
    }
}
