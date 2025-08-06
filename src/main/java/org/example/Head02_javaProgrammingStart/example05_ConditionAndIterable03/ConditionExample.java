package org.example.Head02_javaProgrammingStart.example05_ConditionAndIterable03;

public class ConditionExample {
    public static void main(String[] args) {
        int num = 2;

        if (num % 2 == 0) {
            System.out.println("짝수");
        } else {
            System.out.println("홀수");
        }

        switch (num) {
            case 1:
                System.out.println("One");
                break;
            case 2:
                System.out.println("Two");
                break;
            default:
                System.out.println("Other");
        }
    }
}