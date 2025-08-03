package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example01_Generic_BoundedParams;

public class ShapeExtendExample {
    public static void main(String[] args) {
        // 사용 예시
        ShapeContainer<Circle> container = new ShapeContainer<>();
        container.add(new Circle(2.0));
        container.add(new Circle(3.0));

        for (Shape shape : container.getShapes()) {
           System.out.println("shape.getArea(); : " + shape.getArea());
        }
        System.out.println(container.totalArea());

    }
}
