package com.codeit.javathreadbasicdemo.practice04;

public class RaceConditionDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 4.4 [실습] Race Condition 발생과 synchronized 해결 ===");

        demonstrateUnsafeCounter();

        System.out.println();

        demonstrateSafeCounter();
    }

    private static void demonstrateUnsafeCounter() {

        System.out.println("[UnsafeCounter 테스트]");

        UnsafeCounter counter =
                new UnsafeCounter();

        runCounterTest(counter);
    }

    private static void demonstrateSafeCounter() {

        System.out.println("[SafeCounter 테스트]");

        SafeCounter counter =
                new SafeCounter();

        runCounterTest(counter);
    }

    private static void runCounterTest(
            Counter counter
    ) {

        int threadCount = 10;

        int incrementPerThread = 10_000;

        Thread[] threads =
                new Thread[threadCount];

        long startTime =
                System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {

            threads[i] =
                    new Thread(() -> {

                        for (int j = 0; j < incrementPerThread; j++) {
                            counter.increment();
                        }
                    });

            threads[i].setName(
                    "CounterThread-" + i
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

        int expectedValue =
                threadCount * incrementPerThread;

        int actualValue =
                counter.getCount();

        System.out.println(
                "예상 값: "
                        + expectedValue
        );

        System.out.println(
                "실제 값: "
                        + actualValue
        );

        System.out.println(
                "차이 값: "
                        + (expectedValue - actualValue)
        );

        System.out.println(
                "소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );

        if (expectedValue != actualValue) {

            System.out.println(
                    "⚠️ Race Condition 발생"
            );

        } else {

            System.out.println(
                    "✅ 정상 동작"
            );
        }
    }

    private interface Counter {

        void increment();

        int getCount();
    }

    private static class UnsafeCounter
            implements Counter {

        private int count = 0;

        @Override
        public void increment() {

            /*
             * count++ 는 원자적 연산이 아니다.
             *
             * 실제로는:
             * 1. 현재 값 읽기
             * 2. +1 계산
             * 3. 다시 저장
             *
             * 여러 스레드가 동시에 접근하면
             * 값 유실이 발생할 수 있다.
             */

            count++;
        }

        @Override
        public int getCount() {
            return count;
        }
    }

    private static class SafeCounter
            implements Counter {

        private int count = 0;

        @Override
        public synchronized void increment() {

            /*
             * synchronized 를 사용하면
             * 한 번에 하나의 스레드만
             * increment() 메서드에 진입 가능하다.
             */

            count++;
        }

        @Override
        public synchronized int getCount() {
            return count;
        }
    }
}