package org.example.Head04_Collections.topic05_StreamApiSAdvancedMethod.example08_collect_toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectSquareEvenExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(2, 4, 4, 5, 10, 2);

        List<Integer> processed = numbers.stream()
                .filter(n -> n % 2 == 0)          // 짝수 선별
                .map(n -> n * n)                 // 제곱 변환
                .distinct()                      // 중복 제거
                .collect(Collectors.toList());   // 리스트로 수집

        System.out.println("Processed list: " + processed);
    }
}
