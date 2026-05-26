package com.codeit.springasyncexceptiondemo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncService {

    @Async
    public void asyncVoidException() {

        System.out.println(
                now() + " [AsyncService] void 비동기 시작"
        );

        System.out.println(
                now() + " [AsyncService] 실행 스레드: "
                        + Thread.currentThread().getName()
        );

        sleep(2000);

        throw new RuntimeException(
                "void 비동기 예외 발생"
        );
    }

    @Async
    public CompletableFuture<String> asyncCompletableException() {

        return CompletableFuture.<String>supplyAsync(() -> {

            System.out.println(
                    now() + " [AsyncService] CompletableFuture 시작"
            );

            System.out.println(
                    now() + " [AsyncService] 실행 스레드: "
                            + Thread.currentThread().getName()
            );

            sleep(2000);

            throw new RuntimeException(
                    "CompletableFuture 예외 발생"
            );

        }).exceptionally(ex -> {

            System.out.println(
                    now() + " [exceptionally] 예외 처리: "
                            + ex.getMessage()
            );

            return "fallback-value";

        }).whenComplete((result, ex) -> {

            System.out.println(
                    now() + " [whenComplete] 최종 결과: "
                            + result
            );

        }).handle((result, ex) -> {

            if (ex != null) {

                System.out.println(
                        now() + " [handle] 추가 예외 처리"
                );

                return "handle-fallback";
            }

            return result;
        });
    }

    private void sleep(long millis) {

        try {

            Thread.sleep(millis);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }
    }

    private String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}