package org.example.Head03_oopProgramming.topic02_JavaClassAndObject.example01_constructors;

public class Product {
    private String productId;
    private String name;
    private double price;

    // 생성자
    public Product(String productId, String name, double price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    // Getter 메서드
    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
