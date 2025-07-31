package org.example.Head03_oopProgramming.topic05_InnerClassAndAnonymousClass.example02_NonStaticInnerClass;

public class Outer {
    private int instanceValue = 200;

    // 비정적 내부 클래스
    public class Inner {
        public void printInstanceValue() {
            System.out.println("Outer instanceValue: " + instanceValue);
        }
    }
}

