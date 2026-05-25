package practice04;

import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableFutureDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 3.4 [실습] Callable과 Future로 결과 반환 받기 ===");

        demonstrateCallableResult();

        System.out.println();

        demonstrateFutureBlocking();

        System.out.println();

        demonstrateTimeoutAndCancel();
    }

    private static void demonstrateCallableResult() {

        System.out.println("[Callable 결과 반환 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        Callable<Integer> calculationTask =
                () -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    System.out.println(
                            now() + " [" + threadName + "] 계산 작업 시작"
                    );

                    Thread.sleep(1000);

                    int result = 10 + 20;

                    System.out.println(
                            now() + " [" + threadName + "] 계산 작업 완료"
                    );

                    return result;
                };

        Future<Integer> future =
                executorService.submit(calculationTask);

        try {

            System.out.println(
                    now() + " [main] future.get() 호출"
            );

            Integer result =
                    future.get();

            System.out.println(
                    now() + " [main] 계산 결과: " + result
            );

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();

        } catch (ExecutionException e) {

            System.out.println(
                    now() + " [main] 작업 중 예외 발생: "
                            + e.getCause()
                            .getMessage()
            );

        } finally {

            executorService.shutdown();
        }
    }

    private static void demonstrateFutureBlocking() {

        System.out.println("[Future.get() 블로킹 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        Future<String> longTask =
                executorService.submit(() -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    System.out.println(
                            now() + " [" + threadName + "] 긴 작업 시작"
                    );

                    Thread.sleep(3000);

                    System.out.println(
                            now() + " [" + threadName + "] 긴 작업 완료"
                    );

                    return "긴 작업 완료";
                });

        Future<String> shortTask =
                executorService.submit(() -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    System.out.println(
                            now() + " [" + threadName + "] 짧은 작업 시작"
                    );

                    Thread.sleep(1000);

                    System.out.println(
                            now() + " [" + threadName + "] 짧은 작업 완료"
                    );

                    return "짧은 작업 완료";
                });

        long startTime =
                System.currentTimeMillis();

        try {

            System.out.println(
                    now() + " [main] longTask.get() 호출"
            );

            String longResult =
                    longTask.get();

            System.out.println(
                    now() + " [main] longTask 결과: " + longResult
            );

            System.out.println(
                    now() + " [main] shortTask.get() 호출"
            );

            String shortResult =
                    shortTask.get();

            System.out.println(
                    now() + " [main] shortTask 결과: " + shortResult
            );

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();

        } catch (ExecutionException e) {

            System.out.println(
                    now() + " [main] 작업 중 예외 발생: "
                            + e.getCause()
                            .getMessage()
            );

        } finally {

            executorService.shutdown();
        }

        long endTime =
                System.currentTimeMillis();

        System.out.println(
                now() + " [main] 총 대기 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }

    private static void demonstrateTimeoutAndCancel() {

        System.out.println("[timeout과 cancel 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(1);

        Future<String> slowTask =
                executorService.submit(() -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    try {

                        System.out.println(
                                now() + " [" + threadName + "] 느린 작업 시작"
                        );

                        Thread.sleep(10_000);

                        System.out.println(
                                now() + " [" + threadName + "] 느린 작업 완료"
                        );

                        return "느린 작업 완료";

                    } catch (InterruptedException e) {

                        System.out.println(
                                now() + " [" + threadName + "] 인터럽트로 작업 중단"
                        );

                        Thread.currentThread()
                                .interrupt();

                        return "작업 중단됨";
                    }
                });

        try {

            System.out.println(
                    now() + " [main] slowTask.get(2초 제한) 호출"
            );

            String result =
                    slowTask.get(
                            2,
                            TimeUnit.SECONDS
                    );

            System.out.println(
                    now() + " [main] 작업 결과: " + result
            );

        } catch (TimeoutException e) {

            System.out.println(
                    now() + " [main] 2초 안에 작업이 끝나지 않아 취소합니다."
            );

            boolean cancelled =
                    slowTask.cancel(true);

            System.out.println(
                    now() + " [main] 취소 요청 결과: " + cancelled
            );

            System.out.println(
                    now() + " [main] Future 취소 상태: "
                            + slowTask.isCancelled()
            );

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();

        } catch (ExecutionException e) {

            System.out.println(
                    now() + " [main] 작업 중 예외 발생: "
                            + e.getCause()
                            .getMessage()
            );

        } finally {

            executorService.shutdownNow();
        }
    }

    private static String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}