package org.example.Head04_Collections.topic04_LambdaExpression.example01;

@FunctionalInterface
interface Calculator {
    int operate(int x, int y);
}

public class LambdaSyntaxDemo {
    public static void main(String[] args) {
        // 단일 명령문
        Calculator add = (a, b) -> a + b;

        // 여러 줄 블록
        Calculator subtract = (a, b) -> {
            System.out.println("subtracting...");
            return a - b;
        };

        System.out.println("3 + 5 = " + add.operate(3, 5));
        System.out.println("8 - 2 = " + subtract.operate(8, 2));
    }
}
