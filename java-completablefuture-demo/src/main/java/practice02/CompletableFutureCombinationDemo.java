package practice02;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureCombinationDemo {

    public static void run() {

        System.out.println();
        System.out.println("=== 2.4 [실습] 비동기 작업 조합 패턴 ===");

        // 1. thenCompose()
        // 이전 작업 결과를 다음 비동기 작업에 전달하는 순차 체이닝
        demonstrateThenCompose();

        System.out.println();

        // 2. thenCombine()
        // 서로 독립적인 두 작업 결과를 조합
        demonstrateThenCombine();

        System.out.println();

        // 3. allOf()
        // 여러 작업이 모두 끝날 때까지 대기
        demonstrateAllOf();

        System.out.println();

        // 4. anyOf()
        // 여러 작업 중 가장 먼저 끝난 결과 사용
        demonstrateAnyOf();
    }

    private static void demonstrateThenCompose() {

        System.out.println("[thenCompose 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(3);

        // 회원가입 흐름:
        // 사용자 ID 생성
        // -> 프로필 생성
        // -> 환영 이메일 발송
        // 순서대로 연결된다.
        CompletableFuture<String> signupFuture =
                createUserId(executorService)

                        // 첫 번째 작업 결과(userId)를 다음 비동기 작업으로 전달
                        .thenCompose(userId ->
                                createProfile(userId, executorService)
                        )

                        // 두 번째 작업 결과(profileId)를 다음 비동기 작업으로 전달
                        .thenCompose(profileId ->
                                sendWelcomeEmail(profileId, executorService)
                        );

        // join()
        // 작업이 끝날 때까지 현재 스레드 대기
        String result =
                signupFuture.join();

        System.out.println(
                now() + " [main] 최종 결과: " + result
        );

        executorService.shutdown();
    }

    private static void demonstrateThenCombine() {

        System.out.println("[thenCombine 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(2);

        // 상품 가격 조회
        CompletableFuture<Integer> priceFuture =
                CompletableFuture.supplyAsync(() -> {

                    System.out.println(
                            now() + " [price] 상품 가격 조회 시작"
                    );

                    sleep(2000);

                    System.out.println(
                            now() + " [price] 상품 가격 조회 완료"
                    );

                    return 10000;

                }, executorService);

        // 할인 금액 조회
        CompletableFuture<Integer> discountFuture =
                CompletableFuture.supplyAsync(() -> {

                    System.out.println(
                            now() + " [discount] 할인 금액 조회 시작"
                    );

                    sleep(1500);

                    System.out.println(
                            now() + " [discount] 할인 금액 조회 완료"
                    );

                    return 2000;

                }, executorService);

        // 두 작업은 서로 독립적이므로 병렬 실행 가능
        // 두 결과가 모두 준비되면 price - discount 계산
        Integer finalPrice =
                priceFuture.thenCombine(
                        discountFuture,
                        (price, discount) -> price - discount
                ).join();

        System.out.println(
                now() + " [main] 최종 결제 금액: " + finalPrice
        );

        executorService.shutdown();
    }

    private static void demonstrateAllOf() {

        System.out.println("[allOf 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(3);

        // 서로 독립적인 3개 작업 실행
        CompletableFuture<String> imageFuture =
                loadProductImage(executorService);

        CompletableFuture<String> descriptionFuture =
                loadProductDescription(executorService);

        CompletableFuture<String> reviewFuture =
                loadProductReviews(executorService);

        // 모든 작업이 완료될 때까지 대기
        CompletableFuture<Void> allFuture =
                CompletableFuture.allOf(
                        imageFuture,
                        descriptionFuture,
                        reviewFuture
                );

        // 3개 작업 모두 완료될 때까지 현재 스레드 대기
        allFuture.join();

        System.out.println(
                now() + " [main] 상품 상세 데이터 통합"
        );

        // 이미 allOf()로 완료된 상태이므로 즉시 결과 반환
        System.out.println("이미지: " + imageFuture.join());
        System.out.println("설명: " + descriptionFuture.join());
        System.out.println("리뷰: " + reviewFuture.join());

        executorService.shutdown();
    }

    private static void demonstrateAnyOf() {

        System.out.println("[anyOf 테스트]");

        ExecutorService executorService =
                Executors.newFixedThreadPool(3);

        // 여러 업체에 동시에 견적 요청
        List<CompletableFuture<String>> futures =
                List.of(
                        requestVendor("업체A", 3000, executorService),
                        requestVendor("업체B", 1000, executorService),
                        requestVendor("업체C", 2000, executorService)
                );

        // 가장 먼저 끝난 작업 결과만 사용
        Object fastestResult =
                CompletableFuture.anyOf(
                        futures.toArray(new CompletableFuture[0])
                ).join();

        System.out.println(
                now() + " [main] 가장 빠른 견적: "
                        + fastestResult
        );

        // 나머지 작업은 굳이 기다릴 필요 없으므로 종료 시도
        executorService.shutdownNow();
    }

    private static CompletableFuture<String> createUserId(
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(
                    now() + " [signup] 사용자 ID 생성 시작"
            );

            sleep(1000);

            System.out.println(
                    now() + " [signup] 사용자 ID 생성 완료"
            );

            return "USER-100";

        }, executorService);
    }

    private static CompletableFuture<String> createProfile(
            String userId,
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(
                    now() + " [profile] 프로필 생성 시작: " + userId
            );

            sleep(1000);

            System.out.println(
                    now() + " [profile] 프로필 생성 완료"
            );

            return "PROFILE-" + userId;

        }, executorService);
    }

    private static CompletableFuture<String> sendWelcomeEmail(
            String profileId,
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(
                    now() + " [email] 환영 이메일 발송 시작: " + profileId
            );

            sleep(1000);

            System.out.println(
                    now() + " [email] 환영 이메일 발송 완료"
            );

            return "회원가입 완료";

        }, executorService);
    }

    private static CompletableFuture<String> loadProductImage(
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(
                    now() + " [image] 상품 이미지 조회 시작"
            );

            sleep(1000);

            System.out.println(
                    now() + " [image] 상품 이미지 조회 완료"
            );

            return "product-image.png";

        }, executorService);
    }

    private static CompletableFuture<String> loadProductDescription(
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(
                    now() + " [description] 상품 설명 조회 시작"
            );

            sleep(1500);

            System.out.println(
                    now() + " [description] 상품 설명 조회 완료"
            );

            return "상품 설명 데이터";

        }, executorService);
    }

    private static CompletableFuture<String> loadProductReviews(
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(
                    now() + " [review] 리뷰 조회 시작"
            );

            sleep(2000);

            System.out.println(
                    now() + " [review] 리뷰 조회 완료"
            );

            return "리뷰 128개";

        }, executorService);
    }

    private static CompletableFuture<String> requestVendor(
            String vendorName,
            int delayMillis,
            ExecutorService executorService
    ) {

        return CompletableFuture.supplyAsync(() -> {

            System.out.println(
                    now() + " [" + vendorName + "] 견적 요청 시작"
            );

            sleep(delayMillis);

            System.out.println(
                    now() + " [" + vendorName + "] 견적 응답 완료"
            );

            return vendorName + " 견적: 352,000원";

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