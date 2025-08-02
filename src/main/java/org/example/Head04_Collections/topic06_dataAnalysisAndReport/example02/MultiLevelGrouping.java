package org.example.Head04_Collections.topic06_dataAnalysisAndReport.example02;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Employee {
    private String name;
    private String position;    // 예: "Manager", "Developer"
    private String department;  // 예: "HR", "IT", "Sales"

    public Employee(String name, String position, String department) {
        this.name = name;
        this.position = position;
        this.department = department;
    }
    public String getPosition() { return position; }
    public String getDepartment() { return department; }
    @Override
    public String toString() {
        return name + "(" + position + ", " + department + ")";
    }
}

public class MultiLevelGrouping {
    public static void main(String[] args) {
        List<Employee> emps = Arrays.asList(
                new Employee("Alice", "Manager", "HR"),
                new Employee("Bob", "Developer", "IT"),
                new Employee("Charlie", "Developer", "IT"),
                new Employee("David", "Manager", "Sales"),
                new Employee("Eve", "Developer", "Sales")
        );

        // 1차: position 기준 -> 2차: department 기준
        Map<String, Map<String, List<Employee>>> multiGroup =
                emps.stream().collect(
                        Collectors.groupingBy(
                                Employee::getPosition,
                                Collectors.groupingBy(Employee::getDepartment)
                        ));

        System.out.println(multiGroup);
    }
}
