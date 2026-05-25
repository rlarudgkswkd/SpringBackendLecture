package com.codeit.javathreadbasicdemo.practice05;

public class SynchronizedComparisonDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 4.5 [실습] synchronized 메서드와 블록 동기화 비교 ===");

        demonstrateMethodSynchronization();

        System.out.println();

        demonstrateBlockSynchronization();
    }

    private static void demonstrateMethodSynchronization() {

        System.out.println("[메서드 전체 synchronized 방식]");

        MethodSynchronizedCounter counter =
                new MethodSynchronizedCounter();

        runPerformanceTest(
                counter::increment,
                "MethodSync"
        );
    }

    private static void demonstrateBlockSynchronization() {

        System.out.println("[블록 synchronized 방식]");

        BlockSynchronizedCounter counter =
                new BlockSynchronizedCounter();

        runPerformanceTest(
                counter::increment,
                "BlockSync"
        );
    }

    private static void runPerformanceTest(
            Runnable incrementLogic,
            String testName
    ) {

        int threadCount = 10;

        int operationCount = 10_000;

        Thread[] threads =
                new Thread[threadCount];

        long startTime =
                System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {

            threads[i] =
                    new Thread(() -> {

                        for (int j = 0; j < operationCount; j++) {
                            incrementLogic.run();
                        }
                    });

            threads[i].setName(
                    testName + "-Thread-" + i
            );
        }

        for (Thread thread : threads) {
            thread.start();
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
                testName
                        + " 총 소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );
    }

    private static class MethodSynchronizedCounter {

        private int count = 0;

        /*
         * 메서드 전체에 락 적용
         *
         * increment() 전체가 임계 영역이 된다.
         * 불필요한 코드까지 락 범위에 포함될 수 있다.
         */
        public synchronized void increment() {

            simulateAdditionalWork();

            count++;
        }

        private void simulateAdditionalWork() {

            try {
                Thread.sleep(1);

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();
            }
        }
    }

    private static class BlockSynchronizedCounter {

        private int count = 0;

        private final Object countLock =
                new Object();

        private String status =
                "READY";

        private final Object statusLock =
                new Object();

        /*
         * 필요한 부분만 락 적용
         *
         * count 증가 부분만 synchronized 처리한다.
         * 나머지 작업은 동시에 실행 가능하다.
         */
        public void increment() {

            simulateAdditionalWork();

            synchronized (countLock) {
                count++;
            }
        }

        /*
         * 별도 공유 자원은 별도 락 사용 가능
         */
        public void updateStatus(
                String newStatus
        ) {

            synchronized (statusLock) {
                status = newStatus;
            }
        }

        private void simulateAdditionalWork() {

            try {
                Thread.sleep(1);

            } catch (InterruptedException e) {

                Thread.currentThread()
                        .interrupt();
            }
        }
    }
}