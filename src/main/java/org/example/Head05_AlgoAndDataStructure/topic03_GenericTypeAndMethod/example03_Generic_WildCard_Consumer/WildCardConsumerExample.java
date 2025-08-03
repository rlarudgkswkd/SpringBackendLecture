package org.example.Head05_AlgoAndDataStructure.topic03_GenericTypeAndMethod.example03_Generic_WildCard_Consumer;

import java.util.ArrayList;
import java.util.List;

public class WildCardConsumerExample {
    // List<? super Shape>의 예시: List<Object>, List<Shape>
    public static void addDefaultShapes(List<? super Shape> list) {
        list.add(new Circle(1.0));
        list.add(new Rectangle(2.0, 5.0));
        // Shape s = list.get(0); // 불가능, Object일 수도 있음
    }

    public static void main(String[] args) {
        List<Object> objList = new ArrayList<>(); // List<Shape>, List<Object> 등 Shape의 상위 타입 리스트가 인수로 전달 가능하다.
        addDefaultShapes(objList); // List<Object>는 ? super Shape로 매칭
        for (Object o : objList) {
            if(o instanceof Circle circle) {
                System.out.println("circle.getArea() :" + circle.getArea());
            }
            if(o instanceof Rectangle rectangle) {
                System.out.println("rectangle.getArea() :" + rectangle.getArea());
            }
        }
    }
}
