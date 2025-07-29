package org.example.oopProgramming.topic05_InnerClassAndAnonymousClass.example01_StaticNestedClass;

public class StaticNestedTest {
    public static void main(String[] args) {
        // 정적 중첩 클래스는 외부 클래스의 객체 생성 없이도 바로 사용 가능
        Outer.StaticNested nested = new Outer.StaticNested();
        nested.printValues();
    }
}
