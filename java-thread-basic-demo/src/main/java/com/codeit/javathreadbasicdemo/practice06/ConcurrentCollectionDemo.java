package com.codeit.javathreadbasicdemo.practice06;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentCollectionDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 4.6 [실습] ConcurrentHashMap과 BlockingQueue 활용 ===");

        demonstrateHashMapProblem();

        System.out.println();

        demonstrateConcurrentHashMap();

        System.out.println();

        demonstrateBlockingQueue();
    }

    private static void demonstrateHashMapProblem() {

        System.out.println("[HashMap 멀티스레드 테스트]");

        Map<String, Integer> map =
                new HashMap<>();

        runMapTest(
                map,
                "HashMap"
        );
    }

    private static void demonstrateConcurrentHashMap() {

        System.out.println("[ConcurrentHashMap 멀티스레드 테스트]");

        Map<String, Integer> map =
                new ConcurrentHashMap<>();

        runMapTest(
                map,
                "ConcurrentHashMap"
        );
    }

    private static void runMapTest(
            Map<String, Integer> map,
            String testName
    ) {

        int threadCount = 5;

        int operationCount = 2_000;

        Thread[] threads =
                new Thread[threadCount];

        long startTime =
                System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {

            threads[i] =
                    new Thread(() -> {

                        for (int j = 0; j < operationCount; j++) {

                            String key =
                                    "user-" + (j % 100);

                            map.merge(
                                    key,
                                    1,
                                    Integer::sum
                            );
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

        int expectedTotal =
                threadCount * operationCount;

        int actualTotal =
                map.values()
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum();

        System.out.println(
                "예상 총합: "
                        + expectedTotal
        );

        System.out.println(
                "실제 총합: "
                        + actualTotal
        );

        System.out.println(
                "Map 크기: "
                        + map.size()
        );

        System.out.println(
                "소요 시간: "
                        + (endTime - startTime)
                        + "ms"
        );

        if (expectedTotal != actualTotal) {

            System.out.println(
                    "⚠️ 멀티스레드 환경에서 값 유실 가능성 확인"
            );

        } else {

            System.out.println(
                    "✅ 예상 값과 실제 값이 일치"
            );
        }
    }

    private static void demonstrateBlockingQueue() {

        System.out.println("[BlockingQueue Producer-Consumer 테스트]");

        BlockingQueue<String> taskQueue =
                new LinkedBlockingQueue<>(3);

        Thread producer =
                new Thread(() -> {

                    try {
                        for (int i = 1; i <= 8; i++) {

                            String task =
                                    "Task-" + i;

                            System.out.println(
                                    "[Producer] 작업 생성: "
                                            + task
                            );

                            taskQueue.put(task);

                            System.out.println(
                                    "[Producer] 큐에 추가 완료: "
                                            + task
                                            + ", 현재 큐 크기: "
                                            + taskQueue.size()
                            );

                            Thread.sleep(300);
                        }

                        taskQueue.put("SHUTDOWN");

                    } catch (InterruptedException e) {

                        Thread.currentThread()
                                .interrupt();
                    }
                });

        producer.setName("Producer");

        Thread consumer =
                new Thread(() -> {

                    try {
                        while (true) {

                            String task =
                                    taskQueue.take();

                            if ("SHUTDOWN".equals(task)) {

                                System.out.println(
                                        "[Consumer] 종료 신호 수신"
                                );

                                break;
                            }

                            System.out.println(
                                    "[Consumer] 작업 처리 시작: "
                                            + task
                            );

                            Thread.sleep(1000);

                            System.out.println(
                                    "[Consumer] 작업 처리 완료: "
                                            + task
                                            + ", 현재 큐 크기: "
                                            + taskQueue.size()
                            );
                        }

                    } catch (InterruptedException e) {

                        Thread.currentThread()
                                .interrupt();
                    }
                });

        consumer.setName("Consumer");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }
}
