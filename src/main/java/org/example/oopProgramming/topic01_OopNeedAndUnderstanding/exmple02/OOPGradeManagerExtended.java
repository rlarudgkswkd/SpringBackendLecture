package org.example.oopProgramming.topic01_OopNeedAndUnderstanding.exmple02;

class Student02 {
    String name;
    int mathScore;
    int engScore;
    int sciScore;

    Student02(String name, int mathScore, int engScore, int sciScore) {
        this.name = name;
        this.mathScore = mathScore;
        this.engScore = engScore;
        this.sciScore = sciScore;
    }

    double getAverage() {
        return (mathScore + engScore + sciScore) / 3.0;
    }
}

public class OOPGradeManagerExtended {
    public static void main(String[] args) {
        Student02[] students = {
                new Student02("Alice", 90, 95, 60),
                new Student02("Bob", 80, 75, 70),
                new Student02("Charlie", 85, 88, 90)
        };

        for (Student02 student : students) {
            System.out.printf("%s 평균: %.1f\n", student.name, student.getAverage());
        }
    }
}