package com.codeit.springasyncexceptiondemo.service;

import org.slf4j.MDC;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AsyncService {

    private final AtomicInteger retryCount =
            new AtomicInteger(0);

    @Async
    public void asyncException(
            String email
    ) {

        System.out.println(
                now() + " [AsyncService] 비동기 작업 시작"
        );

        sleep(2000);

        throw new RuntimeException(
                "비동기 이메일 발송 실패"
        );
    }

    @Async
    public CompletableFuture<String>
    asyncCompletableException() {

        return CompletableFuture.<String>supplyAsync(() -> {

            System.out.println(
                    now() + " [AsyncService] CompletableFuture 시작"
            );

            sleep(2000);

            throw new RuntimeException(
                    "CompletableFuture 예외 발생"
            );

        }).exceptionally(ex -> {

            System.out.println(
                    now() + " [exceptionally] 예외 처리"
            );

            return "fallback-value";

        });
    }

    @Async
    @Retryable(
            retryFor = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000)
    )
    public void callExternalApi(
            String email
    ) {

        int count = retryCount.incrementAndGet();

        System.out.println(
                now() + " [RetryService] 재시도 횟수: "
                        + count
        );

        throw new RuntimeException(
                "외부 API 장애"
        );
    }

    @Recover
    public void recover(
            RuntimeException ex,
            String email
    ) {

        System.out.println(
                now() + " [Recover] fallback 실행"
        );
    }

    @Async("mdcTaskExecutor")
    public void asyncMdcTask() {

        System.out.println(
                now() + " [AsyncService] requestId: "
                        + MDC.get("requestId")
        );

        System.out.println(
                now() + " [AsyncService] 실행 스레드: "
                        + Thread.currentThread().getName()
        );
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