package practice01;

import java.time.LocalTime;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureBasicDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 1.4 [실습] CompletableFuture 생성과 기본 체이닝 ===");

        demonstrateRunAsync();

        System.out.println();

        demonstrateSupplyAsyncAndChaining();
    }

    private static void demonstrateRunAsync() {

        System.out.println("[runAsync 테스트]");

        ExecutorService logExecutor =
                Executors.newFixedThreadPool(2);

        CompletableFuture<Void> logFuture =
                CompletableFuture.runAsync(() -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    System.out.println(
                            now() + " [" + threadName + "] 로그 저장 시작"
                    );

                    sleep(1000);

                    System.out.println(
                            now() + " [" + threadName + "] 로그 저장 완료"
                    );

                }, logExecutor);

        logFuture.join();

        logExecutor.shutdown();
    }

    private static void demonstrateSupplyAsyncAndChaining() {

        System.out.println("[supplyAsync + 체이닝 테스트]");

        ExecutorService userExecutor =
                Executors.newFixedThreadPool(2);

        CompletableFuture<Void> future =
                CompletableFuture

                        .supplyAsync(() -> {

                            String threadName =
                                    Thread.currentThread()
                                            .getName();

                            System.out.println(
                                    now() + " [" + threadName + "] 사용자 정보 조회 시작"
                            );

                            sleep(1500);

                            System.out.println(
                                    now() + " [" + threadName + "] 사용자 정보 조회 완료"
                            );

                            return "user-1";

                        }, userExecutor)

                        .thenApply(username -> {

                            System.out.println(
                                    now() + " [thenApply] username 변환"
                            );

                            return username.toUpperCase();
                        })

                        .thenAccept(username -> {

                            System.out.println(
                                    now() + " [thenAccept] 최종 사용자명 출력: "
                                            + username
                            );
                        })

                        .thenRun(() -> {

                            System.out.println(
                                    now() + " [thenRun] 후속 정리 작업 실행"
                            );
                        });

        future.join();

        userExecutor.shutdown();
    }

    private static void sleep(
            long millis
    ) {

        try {

            Thread.sleep(millis);

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}