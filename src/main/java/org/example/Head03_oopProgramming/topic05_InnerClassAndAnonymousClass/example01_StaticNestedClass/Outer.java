package org.example.Head03_oopProgramming.topic05_InnerClassAndAnonymousClass.example01_StaticNestedClass;

public class Outer {
    private static int staticValue = 100;
    private int instanceValue = 50;

    // 정적 중첩 클래스
    public static class StaticNested {
        public void printValues() {
            // 외부 클래스의 static 멤버에는 직접 접근 가능
            System.out.println("Outer.staticValue: " + staticValue);

            // 외부 클래스의 인스턴스 멤버에는 직접 접근 불가능
            // System.out.println(instanceValue); // 컴파일 에러
        }
    }
}

