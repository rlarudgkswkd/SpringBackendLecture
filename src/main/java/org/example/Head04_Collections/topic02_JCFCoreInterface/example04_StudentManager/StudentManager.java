package org.example.Head04_Collections.topic02_JCFCoreInterface.example04_StudentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentManager {
    private List<Student> students;    // 학생 리스트
    private Set<String> studentNames;  // 중복 이름 체크용
    private Map<String, Integer> scoreMap; // 학생 이름 -> 점수

    public StudentManager() {
        students = new ArrayList<>();
        studentNames = new HashSet<>();
        scoreMap = new HashMap<>();
    }

    public void addStudent(Student student, int score) {
        // 중복 이름 체크
        if (studentNames.contains(student.getName())) {
            System.out.println("[Error] 이미 존재하는 학생 이름입니다.");
            return;
        }

        // 등록
        students.add(student);
        studentNames.add(student.getName());
        scoreMap.put(student.getName(), score);

        System.out.println("[Info] 새로운 학생이 등록되었습니다: " + student);
    }

    public void removeStudent(String name) {
        // 이름을 통해 학생 찾기
        Student target = null;
        for (Student s : students) {
            if (s.getName().equals(name)) {
                target = s;
                break;
            }
        }

        if (target != null) {
            students.remove(target);
            studentNames.remove(name);
            scoreMap.remove(name);
            System.out.println("[Info] 학생이 삭제되었습니다: " + name);
        } else {
            System.out.println("[Error] 해당 이름의 학생을 찾을 수 없습니다.");
        }
    }

    public void printAllStudents() {
        System.out.println("=== 학생 목록 ===");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public void printScore(String name) {
        Integer score = scoreMap.get(name);
        if (score != null) {
            System.out.println("[Info] " + name + "의 점수: " + score);
        } else {
            System.out.println("[Error] 점수를 찾을 수 없습니다.");
        }
    }
}
