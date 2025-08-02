package org.example.Head04_Collections.topic06_dataAnalysisAndReport.example01;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupingByExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Kim", "Lee", "Park", "Kang", "Choi", "Cha");

        // 이름의 첫 글자를 기준으로 그룹화
        Map<Character, List<String>> grouped = names.stream()
                .collect(Collectors.groupingBy(name -> name.charAt(0)));

        System.out.println(grouped);
    }
}
