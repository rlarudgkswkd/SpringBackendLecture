package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example03_Generic_WildCard_Consumer;

// 도형 구현 클래스
public class Circle implements Shape {
    private double radius;
    public Circle(double radius) { this.radius = radius; }
    public double getArea() { return Math.PI * radius * radius; }
}
