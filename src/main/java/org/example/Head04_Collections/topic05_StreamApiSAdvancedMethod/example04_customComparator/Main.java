package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example04_customComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Person 객체를 담을 리스트 생성
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Charlie", 25));
        people.add(new Person("Bob", 20));
        people.add(new Person("David", 40));

        System.out.println("=== 정렬 전 (원본 리스트) ===");
        System.out.println(people);

        List<Person> sortedByAge = people.stream()
                .sorted(new PersonAgeComparator())
                .collect(Collectors.toList());
        System.out.println("=== 나이 기준 오름차순 정렬 (Stream + PersonAgeComparator) ===");
        System.out.println(sortedByAge);
    }
}