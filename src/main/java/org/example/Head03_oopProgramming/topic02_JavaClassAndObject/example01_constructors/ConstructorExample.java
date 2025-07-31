package org.example.Head03_oopProgramming.topic02_JavaClassAndObject.example01_constructors;

import java.util.ArrayList;
import java.util.List;

public class ConstructorExample {
    public static void main(String[] args) {
        // 1. 기본 생성자 사용
        Order order1 = new Order();
        System.out.println("order1 ID: " + order1.getOrderId());
        System.out.println("order1 isPaid: " + order1.isPaid());

        // 2. orderId만 전달하는 생성자 사용
        Order order2 = new Order("ORD001");
        System.out.println("order2 ID: " + order2.getOrderId());
        System.out.println("order2 product count: " + order2.getProducts().size());

        if(order1.equals(order2)) {
            System.out.println("order1.equals(order2) : " + order1.equals(order2));
        }

        // 3. orderId와 상품 목록을 전달하는 생성자 사용
        List<Product> productList = new ArrayList<>();
        productList.add(new Product("P001", "Laptop", 1200.0));
        productList.add(new Product("P002", "Mouse", 25.0));

        Order order3 = new Order("ORD002", productList);
        System.out.println("order3 ID: " + order3.getOrderId());
        System.out.println("order3 products: ");
        for (Product p : order3.getProducts()) {
            System.out.println("- " + p.getName() + " ($" + p.getPrice() + ")");
        }
    }
}
