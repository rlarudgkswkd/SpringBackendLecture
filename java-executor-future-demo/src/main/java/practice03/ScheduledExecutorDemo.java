package practice03;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 2.7 [실습] ScheduledThreadPool로 지연 실행과 반복 실행 구현 ===");

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(3);

        demonstrateDelayedTask(scheduler);

        System.out.println();

        demonstrateFixedRateTask(scheduler);

        System.out.println();

        demonstrateFixedDelayTask(scheduler);

        waitAndShutdown(scheduler);
    }

    /*
     * 일정 시간 뒤 한 번 실행
     */
    private static void demonstrateDelayedTask(
            ScheduledExecutorService scheduler
    ) {

        System.out.println("[schedule() 테스트]");

        scheduler.schedule(
                () -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    System.out.println(
                            "schedule() : "
                                    + "[" + threadName + "] "
                                    + currentTime()
                                    + " -> 2초 후 실행된 작업"
                    );

                },
                2,
                TimeUnit.SECONDS
        );
    }

    /*
     * 고정 주기 실행
     *
     * 작업 시작 시간을 기준으로 반복 실행된다.
     */
    private static void demonstrateFixedRateTask(
            ScheduledExecutorService scheduler
    ) {

        System.out.println("[scheduleAtFixedRate() 테스트]");

        scheduler.scheduleAtFixedRate(
                () -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    System.out.println(
                            "scheduleAtFixedRate() : "
                                    + "[" + threadName + "] "
                                    + currentTime()
                                    + " -> 고정 주기 작업 실행"
                    );

                    simulateWork(1000);

                },
                1,
                3,
                TimeUnit.SECONDS
        );
    }

    /*
     * 작업 완료 후 일정 시간 뒤 실행
     *
     * 이전 작업 종료 시점을 기준으로 대기 후 실행된다.
     */
    private static void demonstrateFixedDelayTask(
            ScheduledExecutorService scheduler
    ) {

        System.out.println("[scheduleWithFixedDelay() 테스트]");

        scheduler.scheduleWithFixedDelay(
                () -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    System.out.println(
                            "scheduleWithFixedDelay() : "
                                    + "[" + threadName + "] "
                                    + currentTime()
                                    + " -> Delay 기반 작업 실행"
                    );

                    simulateWork(2000);

                },
                1,
                2,
                TimeUnit.SECONDS
        );
    }

    /*
     * 작업 시뮬레이션
     */
    private static void simulateWork(
            long millis
    ) {

        try {
            Thread.sleep(millis);

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    /*
     * 현재 시간 출력용
     */
    private static String currentTime() {

        return LocalTime.now()
                .withNano(0)
                .toString();
    }

    /*
     * 일정 시간 후 스케줄러 종료
     */
    private static void waitAndShutdown(
            ScheduledExecutorService scheduler
    ) {

        try {

            /*
             * 스케줄 동작 확인용 대기
             */
            Thread.sleep(12000);

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }

        System.out.println();
        System.out.println("스케줄러 종료 작업 시작");

        scheduler.shutdown();

        try {

            boolean terminated =
                    scheduler.awaitTermination(
                            5,
                            TimeUnit.SECONDS
                    );

            if (!terminated) {

                System.out.println(
                        "강제 종료 수행"
                );

                scheduler.shutdownNow();
            }

        } catch (InterruptedException e) {

            scheduler.shutdownNow();

            Thread.currentThread()
                    .interrupt();
        }

        System.out.println("스케줄러 종료 작업 완료");
    }
}