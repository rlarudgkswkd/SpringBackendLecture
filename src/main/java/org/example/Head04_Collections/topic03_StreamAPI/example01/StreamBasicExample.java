package org.example.Head04_Collections.topic03_StreamAPI.example01;

import java.util.Arrays;
import java.util.List;

public class StreamBasicExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Tom", "Jerry", "Kim", "Tommy");

        names.stream()                          // Stream 생성
                .filter(name -> name.startsWith("T")) // 중간 연산 1
                .map(String::toUpperCase)           // 중간 연산 2
                .forEach(System.out::println);      // 최종 연산
    }
}