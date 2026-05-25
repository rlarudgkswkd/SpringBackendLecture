package practice04;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureWorkflowDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 4.5 [실습] CompletableFuture 타임아웃과 복합 워크플로우 ===");

        demonstrateOrTimeout();

        System.out.println();

        demonstrateCompleteOnTimeout();

        System.out.println();

        demonstrateOrderWorkflow();
    }

    private static void demonstrateOrTimeout() {

        System.out.println("[orTimeout 테스트]");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletableFuture<String> paymentFuture =
                CompletableFuture.supplyAsync(() -> {

                    System.out.println(now() + " [payment] 결제 처리 시작");

                    sleep(3000);

                    return "결제 완료";

                }, executorService).orTimeout(2, TimeUnit.SECONDS);

        try {

            String result = paymentFuture.join();

            System.out.println(now() + " [main] 결과: " + result);

        } catch (Exception e) {

            System.out.println(now() + " [main] 타임아웃 발생: " + e.getCause());
        }

        executorService.shutdown();
    }

    private static void demonstrateCompleteOnTimeout() {

        System.out.println("[completeOnTimeout 테스트]");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletableFuture<String> stockFuture =
                CompletableFuture.supplyAsync(() -> {

                    System.out.println(now() + " [stock] 실시간 재고 조회 시작");

                    sleep(3000);

                    return "실시간 재고: 12개";

                }, executorService).completeOnTimeout(
                        "캐시 재고: 10개",
                        2,
                        TimeUnit.SECONDS
                );

        String result = stockFuture.join();

        System.out.println(now() + " [main] 최종 결과: " + result);

        executorService.shutdown();
    }

    private static void demonstrateOrderWorkflow() {

        System.out.println("[복합 주문 처리 워크플로우 테스트]");

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<String> orderWorkflow =
                validateCart(executorService)

                        .thenCompose(cartId ->
                                reserveStock(cartId, executorService)
                        )

                        .thenCompose(stockId ->
                                processPayment(stockId, executorService)
                        )

                        .thenCompose(paymentId ->
                                createDelivery(paymentId, executorService)
                        )

                        .thenCompose(deliveryId -> {

                            CompletableFuture<Void> emailFuture =
                                    sendEmail(deliveryId, executorService);

                            CompletableFuture<Void> smsFuture =
                                    sendSms(deliveryId, executorService);

                            CompletableFuture<Void> logFuture =
                                    saveOrderLog(deliveryId, executorService);

                            return CompletableFuture.allOf(
                                    emailFuture,
                                    smsFuture,
                                    logFuture
                            ).thenApply(v -> "주문 완료: " + deliveryId);
                        });

        String result = orderWorkflow.join();

        System.out.println(now() + " [main] 최종 주문 결과: " + result);

        executorService.shutdown();
    }

    private static CompletableFuture<String> validateCart(
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(now() + " [cart] 장바구니 검증 시작");

            sleep(1000);

            System.out.println(now() + " [cart] 장바구니 검증 완료");

            return "CART-100";

        }, executorService);
    }

    private static CompletableFuture<String> reserveStock(
            String cartId,
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(now() + " [stock] 재고 예약 시작: " + cartId);

            sleep(1000);

            System.out.println(now() + " [stock] 재고 예약 완료");

            return "STOCK-200";

        }, executorService);
    }

    private static CompletableFuture<String> processPayment(
            String stockId,
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(now() + " [payment] 결제 처리 시작: " + stockId);

            sleep(1500);

            System.out.println(now() + " [payment] 결제 완료");

            return "PAYMENT-300";

        }, executorService);
    }

    private static CompletableFuture<String> createDelivery(
            String paymentId,
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(now() + " [delivery] 배송 생성 시작: " + paymentId);

            sleep(1000);

            System.out.println(now() + " [delivery] 배송 생성 완료");

            return "DELIVERY-500";

        }, executorService);
    }

    private static CompletableFuture<Void> sendEmail(
            String deliveryId,
            ExecutorService executorService
    ) {

        return CompletableFuture.runAsync(() -> {

            System.out.println(now() + " [email] 이메일 발송 시작: " + deliveryId);

            sleep(1000);

            System.out.println(now() + " [email] 이메일 발송 완료");

        }, executorService);
    }

    private static CompletableFuture<Void> sendSms(
            String deliveryId,
            ExecutorService executorService
    ) {

        return CompletableFuture.runAsync(() -> {

            System.out.println(now() + " [sms] SMS 발송 시작: " + deliveryId);

            sleep(800);

            System.out.println(now() + " [sms] SMS 발송 완료");

        }, executorService);
    }

    private static CompletableFuture<Void> saveOrderLog(
            String deliveryId,
            ExecutorService executorService
    ) {

        return CompletableFuture.runAsync(() -> {

            System.out.println(now() + " [log] 주문 로그 저장 시작: " + deliveryId);

            sleep(500);

            System.out.println(now() + " [log] 주문 로그 저장 완료");

        }, executorService);
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