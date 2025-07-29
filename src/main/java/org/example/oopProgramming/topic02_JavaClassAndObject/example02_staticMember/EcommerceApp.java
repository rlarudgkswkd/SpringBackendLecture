package org.example.oopProgramming.topic02_JavaClassAndObject.example02_staticMember;


import static org.example.oopProgramming.topic02_JavaClassAndObject.example02_staticMember.Utility.applyDiscount;

public class EcommerceApp {
    public static void main(String[] args) {
        double originalPrice = 100.0;
        double finalPrice = applyDiscount(originalPrice);
        System.out.println("할인 적용된 가격: " + finalPrice);
    }
}
