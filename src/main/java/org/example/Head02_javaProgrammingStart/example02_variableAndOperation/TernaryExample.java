package org.example.Head02_javaProgrammingStart.example02_variableAndOperation;

public class TernaryExample {
    public static void main(String[] args) {
        int score = 92;
        String result = (score >= 90) ? "A학점" : "B학점";
        System.out.println("성적: " + result);
    }
}