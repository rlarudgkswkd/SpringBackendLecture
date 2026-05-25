package practice03;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureExceptionDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 3.5 [실습] CompletableFuture 예외 처리와 회복 ===");

        demonstrateExceptionally();

        System.out.println();

        demonstrateHandle();

        System.out.println();

        demonstrateWhenComplete();
    }

    private static void demonstrateExceptionally() {

        System.out.println("[exceptionally 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        CompletableFuture<String> paymentFuture =
                CompletableFuture

                        .supplyAsync(() -> {

                            System.out.println(
                                    now() + " [payment] 결제 API 호출 시작"
                            );

                            sleep(1000);

                            if (true) {
                                throw new RuntimeException("결제 서버 응답 지연");
                            }

                            return "결제 완료";

                        }, executorService)

                        // 앞 단계에서 예외가 발생하면 실행된다.
                        // 실패한 작업을 기본 메시지로 복구한다.
                        .exceptionally(exception -> {

                            System.out.println(
                                    now() + " [exceptionally] 예외 복구: "
                                            + exception.getMessage()
                            );

                            return "결제 시스템 점검 중입니다.";
                        });

        String result =
                paymentFuture.join();

        System.out.println(
                now() + " [main] 최종 결과: " + result
        );

        executorService.shutdown();
    }

    private static void demonstrateHandle() {

        System.out.println("[handle 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        CompletableFuture<String> resultFuture =
                CompletableFuture

                        .supplyAsync(() -> {

                            System.out.println(
                                    now() + " [order] 주문 처리 시작"
                            );

                            sleep(1000);

                            return "ORDER-100";

                        }, executorService)

                        // handle()은 성공과 실패 모두 처리할 수 있다.
                        // result에는 성공 결과가 들어오고,
                        // exception에는 실패 예외가 들어온다.
                        .handle((result, exception) -> {

                            if (exception != null) {

                                System.out.println(
                                        now() + " [handle] 주문 실패 처리"
                                );

                                return "주문 실패: 다시 시도해주세요.";
                            }

                            System.out.println(
                                    now() + " [handle] 주문 성공 처리: "
                                            + result
                            );

                            return "주문 성공: " + result;
                        });

        System.out.println(
                now() + " [main] 최종 결과: "
                        + resultFuture.join()
        );

        executorService.shutdown();
    }

    private static void demonstrateWhenComplete() {

        System.out.println("[whenComplete 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        CompletableFuture<String> deliveryFuture =
                CompletableFuture

                        .supplyAsync(() -> {

                            System.out.println(
                                    now() + " [delivery] 배송 정보 생성 시작"
                            );

                            sleep(1000);

                            return "DELIVERY-500";

                        }, executorService)

                        // whenComplete()는 결과를 바꾸지 않는다.
                        // 성공 또는 실패 여부를 확인하고 로그만 남길 때 사용한다.
                        .whenComplete((result, exception) -> {

                            if (exception != null) {

                                System.out.println(
                                        now() + " [whenComplete] 실패 로그 기록: "
                                                + exception.getMessage()
                                );

                            } else {

                                System.out.println(
                                        now() + " [whenComplete] 성공 로그 기록: "
                                                + result
                                );
                            }
                        });

        String result =
                deliveryFuture.join();

        System.out.println(
                now() + " [main] 최종 결과: " + result
        );

        executorService.shutdown();
    }

    private static void sleep(long millis) {

        try {

            Thread.sleep(millis);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }

    private static String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}