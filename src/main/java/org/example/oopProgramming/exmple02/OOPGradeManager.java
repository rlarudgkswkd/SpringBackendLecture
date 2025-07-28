package org.example.oopProgramming.exmple02;

class Student {
    String name;
    int mathScore;
    int engScore;

    Student(String name, int mathScore, int engScore) {
        this.name = name;
        this.mathScore = mathScore;
        this.engScore = engScore;
    }

    double getAverage() {
        return (mathScore + engScore) / 2.0;
    }
}

public class OOPGradeManager {
    public static void main(String[] args) {
        Student[] students = {
                new Student("Alice", 90, 95),
                new Student("Bob", 80, 75),
                new Student("Charlie", 85, 88)
        };

        for (Student student : students) {
            System.out.printf("%s 평균: %.1f\n", student.name, student.getAverage());
        }
    }
}