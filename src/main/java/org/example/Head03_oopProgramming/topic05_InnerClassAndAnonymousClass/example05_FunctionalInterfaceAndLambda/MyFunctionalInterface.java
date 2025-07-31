package org.example.Head03_oopProgramming.topic05_InnerClassAndAnonymousClass.example05_FunctionalInterfaceAndLambda;

@FunctionalInterface
public interface MyFunctionalInterface {
    void doSomething(); // 유일한 추상 메소드

    // default, static 메소드는 여러 개 있어도 상관없음
    default void helper() {
        System.out.println("Helper method");
    }
}
