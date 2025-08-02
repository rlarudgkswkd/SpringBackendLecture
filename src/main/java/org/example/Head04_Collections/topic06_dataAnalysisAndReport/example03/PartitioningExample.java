package org.example.Head04_Collections.topic06_dataAnalysisAndReport.example03;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartitioningExample {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(5, 12, 18, 7, 3, 20);

        Map<Boolean, List<Integer>> partitioned = nums.stream()
                .collect(Collectors.partitioningBy(n -> n >= 10));

        System.out.println(partitioned);
    }
}
