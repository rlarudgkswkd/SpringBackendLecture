package practice02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTypesDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 2.6 [실습] 다양한 스레드 풀 유형 비교 ===");

        demonstrateSingleThreadExecutor();

        System.out.println();

        demonstrateFixedThreadPool();

        System.out.println();

        demonstrateCachedThreadPool();
    }

    /*
     * 단일 스레드 실행기
     *
     * 모든 작업이 하나의 스레드에서 순차 실행된다.
     */
    private static void demonstrateSingleThreadExecutor() {

        System.out.println("[SingleThreadExecutor 테스트]");

        ExecutorService executorService =
                Executors.newSingleThreadExecutor();

        executeTasks(
                executorService,
                "Single"
        );
    }

    /*
     * 고정 크기 스레드 풀
     *
     * 정해진 개수의 스레드만 생성된다.
     * 스레드를 재사용한다.
     */
    private static void demonstrateFixedThreadPool() {

        System.out.println("[FixedThreadPool 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(3);

        executeTasks(
                executorService,
                "Fixed"
        );
    }

    /*
     * 캐시 기반 스레드 풀
     *
     * 필요 시 스레드를 계속 생성한다.
     * 유휴 스레드는 재사용된다.
     */
    private static void demonstrateCachedThreadPool() {

        System.out.println("[CachedThreadPool 테스트]");

        ExecutorService executorService =
                Executors.newCachedThreadPool();

        executeTasks(
                executorService,
                "Cached"
        );
    }

    private static void executeTasks(
            ExecutorService executorService,
            String poolType
    ) {

        int taskCount = 10;

        long startTime =
                System.currentTimeMillis();

        for (int i = 0; i < taskCount; i++) {

            int taskId = i + 1;

            executorService.execute(() -> {

                String threadName =
                        Thread.currentThread()
                                .getName();

                System.out.println(
                        "[" + poolType + "] "
                                + threadName
                                + " -> 작업 시작: task-"
                                + taskId
                );

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    Thread.currentThread()
                            .interrupt();
                }

                System.out.println(
                        "[" + poolType + "] "
                                + threadName
                                + " -> 작업 완료: task-"
                                + taskId
                );
            });
        }

        executorService.shutdown();

        try {

            boolean terminated =
                    executorService.awaitTermination(
                            20,
                            TimeUnit.SECONDS
                    );

            if (!terminated) {

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
                "[" + poolType + "] 총 소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }
}
