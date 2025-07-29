package org.example.oopProgramming.topic04_SOLID.example03_LSP_improved;

public class LspTestImproved {
    public static void main(String[] args) {
        Shape rect = new Rectangle(5, 10);
        System.out.println("Rectangle area: " + rect.getArea()); // 50

        Shape square = new Square(5);
        System.out.println("Square area: " + square.getArea()); // 25 (5x5)

        // 이제 'square'는 'rectangle'처럼 setWidth, setHeight를 지원하지 않음.
        // 각자 자신의 불변 계약(정사각형, 직사각형)을 지키도록 설계.
    }
}

