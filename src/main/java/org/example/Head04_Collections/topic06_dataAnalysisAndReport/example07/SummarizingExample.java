package org.example.Head04_Collections.topic06_dataAnalysisAndReport.example07;

import java.util.*;
import java.util.stream.Collectors;

public class SummarizingExample {
    public static void main(String[] args) {
        List<Double> doubleScores = Arrays.asList(82.3, 62.0, 72.3, 64.4, 93.2);
        List<Long> longScores = Arrays.asList(80L, 95L, 70L, 100L, 85L);

        DoubleSummaryStatistics doubleSummaryStatistics = doubleScores.stream()
                .collect(Collectors.summarizingDouble(Double::doubleValue));
        LongSummaryStatistics longSummaryStatistics = longScores.stream()
                .collect(Collectors.summarizingLong(Long::longValue));

        System.out.println("Double Summary Count = " + doubleSummaryStatistics.getCount());
        System.out.println("Double Summary Sum = " + doubleSummaryStatistics.getSum());
        System.out.println("Double Summary Min = " + doubleSummaryStatistics.getMin());
        System.out.println("Double Summary Max = " + doubleSummaryStatistics.getMax());
        System.out.println("Double Summary Average = " + doubleSummaryStatistics.getAverage());

        System.out.println("Long Summary Count = " + longSummaryStatistics.getCount());
        System.out.println("Long Summary Sum = " + longSummaryStatistics.getSum());
        System.out.println("Long Summary Min = " + longSummaryStatistics.getMin());
        System.out.println("Long Summary Max = " + longSummaryStatistics.getMax());
        System.out.println("Long Summary Average = " + longSummaryStatistics.getAverage());
    }
}
