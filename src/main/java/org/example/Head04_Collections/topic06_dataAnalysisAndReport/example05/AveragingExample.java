package org.example.Head04_Collections.topic06_dataAnalysisAndReport.example05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AveragingExample {
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(5, 12, 18, 7);
        List<Double> doubles = Arrays.asList(1.3, 2.0, 3.4, 4.2, 5.0);
        List<Long>  longs = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        double intAvg = ints.stream()
                .collect(Collectors.averagingInt(n -> n));
        double doubleAvg = doubles.stream()
                .collect(Collectors.averagingDouble(n -> n));
        double longAvg = longs.stream()
                .collect(Collectors.averagingLong(n -> n));

        System.out.println("intAvg = " + intAvg);
        System.out.println("doubleAvg = " + doubleAvg);
        System.out.println("longAvg = " + longAvg);
    }
}
