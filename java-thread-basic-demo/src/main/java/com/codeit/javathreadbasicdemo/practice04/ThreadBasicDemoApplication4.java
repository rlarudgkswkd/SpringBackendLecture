package com.codeit.javathreadbasicdemo.practice04;

public class ThreadBasicDemoApplication4 {

    public static void main(String[] args) {

        System.out.println("Java Thread 기초와 동기화 실습 시작");

        RaceConditionDemo.run();

        System.out.println("실습 종료");
    }
}
