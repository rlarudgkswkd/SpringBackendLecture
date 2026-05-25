package practice01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadVsExecutorDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 1.5 [실습] 직접 스레드 생성 방식과 Executor 방식 비교 ===");

        demonstrateDirectThreadCreation();

        System.out.println();

        demonstrateExecutorService();
    }

    private static void demonstrateDirectThreadCreation() {

        System.out.println("[직접 Thread 생성 방식]");

        int taskCount = 20;

        Thread[] threads =
                new Thread[taskCount];

        long startTime =
                System.currentTimeMillis();

        for (int i = 0; i < taskCount; i++) {

            int taskId = i + 1;

            threads[i] =
                    new Thread(() -> processRequest(taskId));

            threads[i].setName(
                    "DirectThread-" + taskId
            );

            threads[i].start();
        }

        for (Thread thread : threads) {

            try {
                thread.join();

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();
            }
        }

        long endTime =
                System.currentTimeMillis();

        System.out.println(
                "직접 Thread 생성 방식 총 소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }

    private static void demonstrateExecutorService() {

        System.out.println("[ExecutorService 방식]");

        int taskCount = 20;

        int poolSize = 5;

        ExecutorService executorService =
                Executors.newFixedThreadPool(poolSize);

        long startTime =
                System.currentTimeMillis();

        for (int i = 0; i < taskCount; i++) {

            int taskId = i + 1;

            executorService.execute(
                    () -> processRequest(taskId)
            );
        }

        executorService.shutdown();

        try {
            boolean terminated =
                    executorService.awaitTermination(
                            10,
                            TimeUnit.SECONDS
                    );

            if (!terminated) {

                System.out.println(
                        "지정된 시간 안에 작업이 끝나지 않아 강제 종료합니다."
                );

                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {

            executorService.shutdownNow();

            Thread.currentThread()
                    .interrupt();
        }

        long endTime =
                System.currentTimeMillis();

        System.out.println(
                "ExecutorService 방식 총 소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }

    private static void processRequest(
            int taskId
    ) {

        String threadName =
                Thread.currentThread()
                        .getName();

        System.out.println(
                "[" + threadName + "] 요청 처리 시작: task-" + taskId
        );

        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {

            System.out.println(
                    "[" + threadName + "] 요청 처리 중 인터럽트 발생"
            );

            Thread.currentThread()
                    .interrupt();

            return;
        }

        System.out.println(
                "[" + threadName + "] 요청 처리 완료: task-" + taskId
        );
    }
}