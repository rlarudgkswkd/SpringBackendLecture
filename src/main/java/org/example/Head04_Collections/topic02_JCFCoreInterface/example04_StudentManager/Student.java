package org.example.Head04_Collections.topic02_JCFCoreInterface.example04_StudentManager;

public class Student {
    private String name;
    private int age;
    private String studentId;

    public Student(String name, int age, String studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }

    // Getter/Setter
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age
                + ", studentId='" + studentId + "'}";
    }
}

