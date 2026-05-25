package practice05;

import java.time.LocalTime;

import java.util.Arrays;
import java.util.List;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultipleFutureDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 3.5 [실습] invokeAll과 invokeAny로 여러 Future 관리하기 ===");

        demonstrateInvokeAll();

        System.out.println();

        demonstrateInvokeAny();
    }

    private static void demonstrateInvokeAll() {

        System.out.println("[invokeAll 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(3);

        // 여러 Callable 작업 생성
        List<Callable<String>> tasks =
                Arrays.asList(

                        createTask(
                                "사용자 정보 조회",
                                2000
                        ),

                        createTask(
                                "주문 내역 조회",
                                3000
                        ),

                        createTask(
                                "포인트 조회",
                                1000
                        )
                );

        long startTime =
                System.currentTimeMillis();

        try {

            System.out.println(
                    now() + " [main] invokeAll() 호출"
            );

            // 모든 작업이 끝날 때까지 대기
            List<Future<String>> futures =
                    executorService.invokeAll(tasks);

            System.out.println(
                    now() + " [main] 모든 작업 완료"
            );

            // Future 결과 순차 조회
            for (Future<String> future : futures) {

                String result =
                        future.get();

                System.out.println(
                        now() + " [main] 결과 확인: "
                                + result
                );
            }

        } catch (Exception e) {

            System.out.println(
                    now() + " [main] 예외 발생: "
                            + e.getMessage()
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

    private static void demonstrateInvokeAny() {

        System.out.println("[invokeAny 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(3);

        // 가장 빨리 끝나는 작업 결과만 사용
        List<Callable<String>> tasks =
                Arrays.asList(

                        createTask(
                                "서버1 요청",
                                4000
                        ),

                        createTask(
                                "서버2 요청",
                                2000
                        ),

                        createTask(
                                "서버3 요청",
                                3000
                        )
                );

        long startTime =
                System.currentTimeMillis();

        try {

            System.out.println(
                    now() + " [main] invokeAny() 호출"
            );

            // 가장 먼저 완료된 작업 결과 반환
            String fastestResult =
                    executorService.invokeAny(tasks);

            System.out.println(
                    now() + " [main] 가장 빠른 결과: "
                            + fastestResult
            );

        } catch (Exception e) {

            System.out.println(
                    now() + " [main] 예외 발생: "
                            + e.getMessage()
            );

        } finally {

            executorService.shutdownNow();
        }

        long endTime =
                System.currentTimeMillis();

        System.out.println(
                now() + " [main] 총 대기 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }

    private static Callable<String> createTask(
            String taskName,
            int sleepMillis
    ) {

        return () -> {

            String threadName =
                    Thread.currentThread()
                            .getName();

            System.out.println(
                    now()
                            + " ["
                            + threadName
                            + "] "
                            + taskName
                            + " 시작"
            );

            Thread.sleep(sleepMillis);

            System.out.println(
                    now()
                            + " ["
                            + threadName
                            + "] "
                            + taskName
                            + " 완료"
            );

            return taskName + " 결과";
        };
    }

    private static String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}