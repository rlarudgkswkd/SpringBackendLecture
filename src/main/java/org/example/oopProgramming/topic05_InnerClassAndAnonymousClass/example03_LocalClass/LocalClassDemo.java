package org.example.oopProgramming.topic05_InnerClassAndAnonymousClass.example03_LocalClass;

public class LocalClassDemo {
    public void executeTask() {
        final int taskId = 999;  // Java 8 이후로는 사실상 final이면 가능

        // 메소드 내부에 클래스 정의
        class LocalWorker {
            public void run() {
                System.out.println("LocalWorker started with taskId = " + taskId);
            }
        }

        LocalWorker worker = new LocalWorker();
        worker.run();
    }

    public static void main(String[] args) {
        LocalClassDemo demo = new LocalClassDemo();
        demo.executeTask();
    }
}
