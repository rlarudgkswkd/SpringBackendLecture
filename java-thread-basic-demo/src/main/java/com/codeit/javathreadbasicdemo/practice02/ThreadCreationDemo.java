package com.codeit.javathreadbasicdemo.practice02;

public class ThreadCreationDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 2.4 [실습] Thread 상속 방식과 Runnable 방식 비교 ===");

        demonstrateThreadInheritance();

        System.out.println();

        demonstrateRunnableImplementation();

        System.out.println();

        demonstrateLambdaRunnable();

        System.out.println();

        demonstrateStartAndRunDifference();
    }

    private static void demonstrateThreadInheritance() {

        System.out.println("[Thread 상속 방식]");

        CountingThread countingThread =
                new CountingThread(
                        "상속방식",
                        1,
                        3,
                        500
                );

        countingThread.start();

        try {
            countingThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static void demonstrateRunnableImplementation() {

        System.out.println("[Runnable 구현 방식]");

        CountingTask countingTask =
                new CountingTask(
                        "구현방식",
                        10,
                        12,
                        500
                );

        Thread thread =
                new Thread(countingTask);

        thread.setName("RunnableCountingThread");

        thread.start();

        try {
            thread.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static void demonstrateLambdaRunnable() {

        System.out.println("[람다식 Runnable 방식]");

        Thread lambdaThread =
                new Thread(() -> {

                    for (int i = 1; i <= 3; i++) {

                        System.out.println(
                                "["
                                        + Thread.currentThread().getName()
                                        + "] 람다 작업 "
                                        + i
                                        + " 수행"
                        );

                        try {
                            Thread.sleep(500);

                        } catch (InterruptedException e) {

                            Thread.currentThread()
                                    .interrupt();

                            return;
                        }
                    }
                });

        lambdaThread.setName("LambdaRunnableThread");

        lambdaThread.start();

        try {
            lambdaThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static void demonstrateStartAndRunDifference() {

        System.out.println("[run() 직접 호출과 start() 호출 비교]");

        Thread runThread =
                new Thread(() ->
                        System.out.println(
                                "run() 실행 스레드: "
                                        + Thread.currentThread()
                                        .getName()
                        )
                );

        runThread.setName("RunTestThread");

        runThread.run();

        Thread startThread =
                new Thread(() ->
                        System.out.println(
                                "start() 실행 스레드: "
                                        + Thread.currentThread()
                                        .getName()
                        )
                );

        startThread.setName("StartTestThread");

        startThread.start();

        try {
            startThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static class CountingThread extends Thread {

        private final String taskName;

        private final int start;

        private final int end;

        private final int delayMillis;

        public CountingThread(
                String taskName,
                int start,
                int end,
                int delayMillis
        ) {

            this.taskName = taskName;
            this.start = start;
            this.end = end;
            this.delayMillis = delayMillis;

            setName("CountingThread-" + taskName);
        }

        @Override
        public void run() {

            System.out.println(
                    "[" + getName() + "] 카운팅 시작"
            );

            for (int i = start; i <= end; i++) {

                System.out.println(
                        "[" + getName() + "] 현재 값: "
                                + i
                );

                try {
                    Thread.sleep(delayMillis);

                } catch (InterruptedException e) {

                    Thread.currentThread()
                            .interrupt();

                    return;
                }
            }

            System.out.println(
                    "[" + getName() + "] 카운팅 완료"
            );
        }
    }

    private static class CountingTask implements Runnable {

        private final String taskName;

        private final int start;

        private final int end;

        private final int delayMillis;

        public CountingTask(
                String taskName,
                int start,
                int end,
                int delayMillis
        ) {

            this.taskName = taskName;
            this.start = start;
            this.end = end;
            this.delayMillis = delayMillis;
        }

        @Override
        public void run() {

            String threadName =
                    Thread.currentThread()
                            .getName();

            System.out.println(
                    "[" + threadName + "] "
                            + taskName
                            + " 카운팅 시작"
            );

            for (int i = start; i <= end; i++) {

                System.out.println(
                        "[" + threadName + "] 현재 값: "
                                + i
                );

                try {
                    Thread.sleep(delayMillis);

                } catch (InterruptedException e) {

                    Thread.currentThread()
                            .interrupt();

                    return;
                }
            }

            System.out.println(
                    "[" + threadName + "] "
                            + taskName
                            + " 카운팅 완료"
            );
        }
    }
}
