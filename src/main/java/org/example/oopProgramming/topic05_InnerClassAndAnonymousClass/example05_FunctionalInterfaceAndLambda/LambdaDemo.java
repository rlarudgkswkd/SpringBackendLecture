package org.example.oopProgramming.topic05_InnerClassAndAnonymousClass.example05_FunctionalInterfaceAndLambda;

public class LambdaDemo {
    public static void main(String[] args) {
        // 익명 클래스 방식
        MyCalculator addAnon = new MyCalculator() {
            @Override
            public int operate(int x, int y) {
                return x + y;
            }
        };
        System.out.println("익명 클래스: 3 + 5 = " + addAnon.operate(3, 5));

        // 람다식 방식
        MyCalculator addLambda = (x, y) -> x + y;
        System.out.println("람다: 3 + 5 = " + addLambda.operate(3, 5));
    }
}
