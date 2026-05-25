package com.codeit.javathreadbasicdemo.practice03;

public class ThreadLifecycleDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 3.5 [실습] 스레드 제어와 생명주기 확인 ===");

        // start(), sleep(), join() 실습 실행
        demonstrateStartSleepJoin();

        System.out.println();

        // interrupt() 실습 실행
        demonstrateInterrupt();

        System.out.println();

        // Thread.State 실습 실행
        demonstrateThreadState();
    }

    /**
     * start(), sleep(), join() 동작 확인 실습
     */
    private static void demonstrateStartSleepJoin() {

        System.out.println("[start(), sleep(), join() 확인]");

        // 새로운 작업 스레드 생성
        Thread workerThread =
                new Thread(() -> {

                    // 현재 실행 중인 스레드 이름 조회
                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    try {

                        // 총 3단계 작업 수행
                        for (int i = 1; i <= 3; i++) {

                            System.out.println(
                                    "[" + threadName + "] 작업 단계 "
                                            + i
                                            + "/3"
                            );

                            // 현재 스레드를 1초 동안 잠시 멈춤
                            // 상태: TIMED_WAITING
                            Thread.sleep(1000);
                        }

                        System.out.println(
                                "[" + threadName + "] 작업 완료"
                        );

                    } catch (InterruptedException e) {

                        // sleep 중 interrupt 발생 시 실행됨
                        System.out.println(
                                "[" + threadName + "] 작업 중 인터럽트 발생"
                        );

                        // interrupt 상태 다시 복원
                        Thread.currentThread()
                                .interrupt();
                    }
                });

        // 스레드 이름 지정
        workerThread.setName("WorkerThread");

        // start() 호출 전 상태
        // NEW 상태
        System.out.println(
                "start() 호출 전 상태: "
                        + workerThread.getState()
        );

        // 스레드 실행 시작
        // NEW -> RUNNABLE
        workerThread.start();

        // 실행 직후 상태 출력
        System.out.println(
                "start() 호출 후 상태: "
                        + workerThread.getState()
        );

        try {

            System.out.println(
                    "main 스레드가 WorkerThread 종료를 기다린다."
            );

            // WorkerThread가 끝날 때까지
            // main 스레드가 대기
            workerThread.join();

            // 작업 종료 후 상태
            // TERMINATED 상태
            System.out.println(
                    "join() 이후 상태: "
                            + workerThread.getState()
            );

        } catch (InterruptedException e) {

            // main 스레드 interrupt 처리
            Thread.currentThread()
                    .interrupt();
        }
    }

    /**
     * interrupt() 동작 확인 실습
     */
    private static void demonstrateInterrupt() {

        System.out.println("[interrupt() 확인]");

        // 오래 실행되는 작업 스레드 생성
        Thread longRunningThread =
                new Thread(() -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    try {

                        // 10번 반복 작업 수행
                        for (int i = 1; i <= 10; i++) {

                            System.out.println(
                                    "[" + threadName + "] 진행률: "
                                            + (i * 10)
                                            + "%"
                            );

                            // 0.5초 대기
                            // 이 시점에 interrupt 발생 가능
                            Thread.sleep(500);
                        }

                    } catch (InterruptedException e) {

                        // sleep 중 interrupt 신호 감지
                        System.out.println(
                                "[" + threadName + "] sleep 중 interrupt 신호 감지"
                        );

                        // interrupt 상태 복원
                        Thread.currentThread()
                                .interrupt();
                    }

                    System.out.println(
                            "[" + threadName + "] 작업 종료"
                    );
                });

        // 스레드 이름 지정
        longRunningThread.setName("LongRunningThread");

        // 스레드 실행 시작
        longRunningThread.start();

        try {

            // main 스레드 2초 대기
            Thread.sleep(2000);

            System.out.println(
                    "[main] 2초 후 interrupt() 호출"
            );

            // 작업 스레드에 interrupt 신호 전달
            // 강제 종료가 아니라 중단 요청 신호
            longRunningThread.interrupt();

            // 작업 종료까지 대기
            longRunningThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    /**
     * Thread.State 상태 변화 확인 실습
     */
    private static void demonstrateThreadState() {

        System.out.println("[Thread.State 확인]");

        // wait() 실습용 lock 객체
        Object lock =
                new Object();

        // 상태 변화를 관찰할 스레드
        Thread stateThread =
                new Thread(() -> {

                    String threadName =
                            Thread.currentThread()
                                    .getName();

                    try {

                        System.out.println(
                                "[" + threadName + "] 실행 시작"
                        );

                        // 1.5초 sleep
                        // 상태: TIMED_WAITING
                        Thread.sleep(1500);

                        // lock 획득
                        synchronized (lock) {

                            System.out.println(
                                    "[" + threadName + "] wait 상태 진입"
                            );

                            // 최대 1초 동안 wait
                            // 상태: TIMED_WAITING
                            lock.wait(1000);
                        }

                        System.out.println(
                                "[" + threadName + "] 실행 종료"
                        );

                    } catch (InterruptedException e) {

                        Thread.currentThread()
                                .interrupt();
                    }
                });

        stateThread.setName("StateThread");

        // StateThread 상태를 계속 감시하는 스레드
        Thread monitorThread =
                new Thread(() -> {

                    try {

                        // StateThread 종료 전까지 반복
                        while (
                                stateThread.getState()
                                        != Thread.State.TERMINATED
                        ) {

                            System.out.println(
                                    "[MonitorThread] StateThread 상태: "
                                            + stateThread.getState()
                            );

                            // 0.3초마다 상태 확인
                            Thread.sleep(300);
                        }

                        // 마지막 TERMINATED 상태 출력
                        System.out.println(
                                "[MonitorThread] StateThread 상태: "
                                        + stateThread.getState()
                        );

                    } catch (InterruptedException e) {

                        Thread.currentThread()
                                .interrupt();
                    }
                });

        monitorThread.setName("MonitorThread");

        // 시작 전 상태
        // NEW 상태
        System.out.println(
                "stateThread 시작 전 상태: "
                        + stateThread.getState()
        );

        // 모니터링 스레드 시작
        monitorThread.start();

        // 실제 작업 스레드 시작
        stateThread.start();

        try {

            // 두 스레드 종료까지 대기
            stateThread.join();
            monitorThread.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }
}