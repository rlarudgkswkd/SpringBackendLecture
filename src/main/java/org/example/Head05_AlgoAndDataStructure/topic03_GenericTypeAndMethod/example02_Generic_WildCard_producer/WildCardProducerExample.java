package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example02_Generic_WildCard_producer;

import java.util.ArrayList;
import java.util.List;

public class WildCardProducerExample {
    // List<? extends Shape>의 예시: List<Circle>, List<Rectangle>
    public static void printAllShapes(List<? extends Shape> shapes) {
        for (Shape s : shapes) {
            System.out.println(s.getArea());
        }
        // shapes.add(new Circle(2.0)); // 컴파일 에러. ? extends Shape에 삽입 불가능
    }

    public static void main(String[] args) {
        List<Circle> circleList = new ArrayList<>();
        circleList.add(new Circle(3.0));
        circleList.add(new Circle(4.0));
        circleList.add(new Circle(5.0));

        printAllShapes(circleList); // OK. Circle is-a Shape
    }
}
