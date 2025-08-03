package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example03_Generic_WildCard_Consumer;

public class Rectangle implements Shape {
    private double width;
    private double height;
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }
    public double getArea() {
        return width * height;
    }
}

