package org.example.oopProgramming.topic02_JavaClassAndObject.example03_varargs;

public class VarargsExample {
    public static void main(String[] args) {
        Example ex = new Example();
        ex.print(1, 2);  // fixed two ints 호출
    }
}

class Example {
    void print(int a, int b) { System.out.println("fixed two ints"); }
    void print(int... nums) { System.out.println("varargs ints"); }
}

//print(1, 2) 호출 시, varargs보다 일반 메서드가 우선되어 fixed two ints 출력됨