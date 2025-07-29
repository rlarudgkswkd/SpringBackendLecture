package org.example.oopProgramming.topic05_InnerClassAndAnonymousClass.example06_AnonymousClassPractice;

public class AnonymousClassDemo {
    public static void main(String[] args) {
        // 추상 클래스 익명 구현
        AbstractTask customTask = new AbstractTask() {
            @Override
            public void execute() {
                System.out.println("추상 클래스의 execute() 구현부");
            }

            @Override
            public void start() {
                System.out.println("Task 시작 변경");
            }
        };

        customTask.start();
        customTask.execute();
    }
}
