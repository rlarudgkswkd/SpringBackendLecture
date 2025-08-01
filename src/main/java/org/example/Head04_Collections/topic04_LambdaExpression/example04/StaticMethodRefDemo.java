package org.example.Head04_Collections.topic04_LambdaExpression.example04;

import java.util.function.Function;

public class StaticMethodRefDemo {
    public static int doubleValue(int x) {
        return x * 2;
    }



    public static void main(String[] args) {
        // 람다 식
        // Function<Integer, Integer> f1 = num -> StaticMethodRefDemo.doubleValue(num);

        // 정적 메서드 참조
        Function<Integer, Integer> f2 = StaticMethodRefDemo::doubleValue;

        System.out.println(f2.apply(10)); // 20
    }
}
