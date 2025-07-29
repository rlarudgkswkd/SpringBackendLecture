package org.example.oopProgramming.topic01_OopNeedAndUnderstanding.exmple01;

public class ProceduralGradeManager {
    public static void main(String[] args) {
        String[] studentNames = {"Alice", "Bob", "Charlie"};
        int[] mathScores = {90, 80, 85};
        int[] engScores = {95, 75, 88};

        for (int i = 0; i < studentNames.length; i++) {
            int total = mathScores[i] + engScores[i];
            double average = total / 2.0;
            System.out.printf("%s 평균: %.1f\n", studentNames[i], average);
        }
    }
}