package practice06;

import java.time.LocalTime;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerShutdownDemo {

    /*
     * Consumer 종료를 위한 특별한 신호 문자열
     *
     * Consumer는 queue.take()로 데이터를 계속 가져오는데,
     * 더 이상 처리할 주문이 없다는 것을 알려주기 위해 사용한다.
     *
     * 이런 종료용 데이터를
     * Poison Pill Pattern이라고도 부른다.
     */
    private static final String SHUTDOWN_SIGNAL =
            "SHUTDOWN";

    public static void run() {

        System.out.println();

        System.out.println(
                "=== 4.4 [실습] Producer-Consumer와 우아한 종료 구현 ==="
        );

        demonstrateGracefulShutdown();
    }

    private static void demonstrateGracefulShutdown() {

        /*
         * BlockingQueue 생성
         *
         * 최대 5개의 데이터까지만 저장 가능하다.
         *
         * Queue가 가득 차면 put()이 대기하고,
         * Queue가 비어 있으면 take()가 대기한다.
         */
        BlockingQueue<String> orderQueue =
                new ArrayBlockingQueue<>(5);

        /*
         * Producer용 스레드 풀
         *
         * 주문 생성 담당
         */
        ExecutorService producerPool =
                Executors.newFixedThreadPool(2);

        /*
         * Consumer용 스레드 풀
         *
         * 주문 처리 담당
         */
        ExecutorService consumerPool =
                Executors.newFixedThreadPool(3);

        int producerCount = 2;

        int consumerCount = 3;

        /*
         * Producer 실행
         */
        for (int i = 1; i <= producerCount; i++) {

            int producerId = i;

            /*
             * submit()으로 비동기 작업 제출
             */
            producerPool.submit(() ->
                    produceOrders(
                            producerId,
                            orderQueue
                    )
            );
        }

        /*
         * Consumer 실행
         */
        for (int i = 1; i <= consumerCount; i++) {

            int consumerId = i;

            consumerPool.submit(() ->
                    consumeOrders(
                            consumerId,
                            orderQueue
                    )
            );
        }

        /*
         * Producer 종료 대기
         *
         * Producer가 주문 생성을 모두 끝낼 때까지 기다린다.
         */
        shutdownProducerPool(
                producerPool
        );

        /*
         * Consumer 종료 신호 전달
         *
         * Consumer 개수만큼 종료 신호를 넣어야 한다.
         *
         * 이유:
         * Consumer 하나가 SHUTDOWN_SIGNAL 하나를 소비하기 때문이다.
         */
        sendShutdownSignals(
                orderQueue,
                consumerCount
        );

        /*
         * Consumer 종료 대기
         */
        shutdownConsumerPool(
                consumerPool
        );
    }

    private static void produceOrders(
            int producerId,
            BlockingQueue<String> orderQueue
    ) {

        try {

            /*
             * 주문 5개 생성
             */
            for (int i = 1; i <= 5; i++) {

                String order =
                        "Order-P"
                                + producerId
                                + "-"
                                + i;

                System.out.println(
                        now()
                                + " [Producer-"
                                + producerId
                                + "] 주문 생성: "
                                + order
                );

                /*
                 * Queue에 주문 저장
                 *
                 * Queue가 가득 차면
                 * 여기서 자동으로 대기한다.
                 */
                orderQueue.put(order);

                System.out.println(
                        now()
                                + " [Producer-"
                                + producerId
                                + "] 큐 저장 완료: "
                                + order
                                + ", 현재 큐 크기: "
                                + orderQueue.size()
                );

                /*
                 * 주문 생성 속도 시뮬레이션
                 */
                Thread.sleep(500);
            }

            System.out.println(
                    now()
                            + " [Producer-"
                            + producerId
                            + "] 주문 생성 완료"
            );

        } catch (InterruptedException e) {

            /*
             * interrupt 발생 시
             * 현재 스레드 interrupt 상태 복원
             */
            System.out.println(
                    now()
                            + " [Producer-"
                            + producerId
                            + "] 인터럽트 발생"
            );

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static void consumeOrders(
            int consumerId,
            BlockingQueue<String> orderQueue
    ) {

        try {

            /*
             * Consumer는 종료 신호가 올 때까지 계속 반복
             */
            while (true) {

                System.out.println(
                        now()
                                + " [Consumer-"
                                + consumerId
                                + "] 주문 대기 중"
                );

                /*
                 * Queue에서 데이터 가져오기
                 *
                 * Queue가 비어 있으면
                 * 여기서 자동으로 대기한다.
                 */
                String order =
                        orderQueue.take();

                /*
                 * 종료 신호 확인
                 */
                if (SHUTDOWN_SIGNAL.equals(order)) {

                    System.out.println(
                            now()
                                    + " [Consumer-"
                                    + consumerId
                                    + "] 종료 신호 수신"
                    );

                    break;
                }

                System.out.println(
                        now()
                                + " [Consumer-"
                                + consumerId
                                + "] 주문 처리 시작: "
                                + order
                );

                /*
                 * 주문 처리 시간 시뮬레이션
                 *
                 * Producer보다 Consumer가 느리도록 설정
                 */
                Thread.sleep(1500);

                System.out.println(
                        now()
                                + " [Consumer-"
                                + consumerId
                                + "] 주문 처리 완료: "
                                + order
                                + ", 현재 큐 크기: "
                                + orderQueue.size()
                );
            }

        } catch (InterruptedException e) {

            System.out.println(
                    now()
                            + " [Consumer-"
                            + consumerId
                            + "] 인터럽트 발생"
            );

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static void shutdownProducerPool(
            ExecutorService producerPool
    ) {

        /*
         * 새로운 작업 제출 차단
         *
         * 이미 제출된 작업은 계속 수행된다.
         */
        producerPool.shutdown();

        try {

            /*
             * 최대 10초 동안
             * Producer 작업 종료 대기
             */
            boolean finished =
                    producerPool.awaitTermination(
                            10,
                            TimeUnit.SECONDS
                    );

            if (finished) {

                System.out.println(
                        now()
                                + " [main] 모든 Producer 작업 완료"
                );

            } else {

                /*
                 * 시간 초과 시 강제 종료 시도
                 */
                System.out.println(
                        now()
                                + " [main] Producer 종료 시간 초과. 강제 종료"
                );

                producerPool.shutdownNow();
            }

        } catch (InterruptedException e) {

            producerPool.shutdownNow();

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static void sendShutdownSignals(
            BlockingQueue<String> orderQueue,
            int consumerCount
    ) {

        try {

            /*
             * Consumer 수만큼 종료 신호 전달
             */
            for (int i = 0; i < consumerCount; i++) {

                orderQueue.put(
                        SHUTDOWN_SIGNAL
                );
            }

            System.out.println(
                    now()
                            + " [main] Consumer 종료 신호 전송 완료"
            );

        } catch (InterruptedException e) {

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static void shutdownConsumerPool(
            ExecutorService consumerPool
    ) {

        /*
         * Consumer 종료 시작
         */
        consumerPool.shutdown();

        try {

            /*
             * 최대 10초 동안 종료 대기
             */
            boolean finished =
                    consumerPool.awaitTermination(
                            10,
                            TimeUnit.SECONDS
                    );

            if (finished) {

                System.out.println(
                        now()
                                + " [main] 모든 Consumer 작업 완료"
                );

            } else {

                /*
                 * 종료 시간 초과 시 강제 종료
                 */
                System.out.println(
                        now()
                                + " [main] Consumer 종료 시간 초과. 강제 종료"
                );

                consumerPool.shutdownNow();
            }

        } catch (InterruptedException e) {

            consumerPool.shutdownNow();

            Thread.currentThread()
                    .interrupt();
        }
    }

    private static String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}