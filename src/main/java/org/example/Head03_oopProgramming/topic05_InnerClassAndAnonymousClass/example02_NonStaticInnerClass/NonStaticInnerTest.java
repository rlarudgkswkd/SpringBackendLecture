package org.example.Head03_oopProgramming.topic05_InnerClassAndAnonymousClass.example02_NonStaticInnerClass;

public class NonStaticInnerTest {
    public static void main(String[] args) {
        // 비정적 내부 클래스를 사용하기 위해서는 외부 클래스의 객체부터 생성
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();  // 외부 객체를 통해 'new Inner()' 호출
        inner.printInstanceValue();
    }
}
