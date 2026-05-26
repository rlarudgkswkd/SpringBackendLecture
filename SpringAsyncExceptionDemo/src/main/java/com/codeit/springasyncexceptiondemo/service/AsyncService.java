package com.codeit.springasyncexceptiondemo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AsyncService {

    @Async
    public void asyncException(
            String email
    ) {

        System.out.println(
                now() + " [AsyncService] 비동기 작업 시작"
        );

        System.out.println(
                now() + " [AsyncService] 실행 스레드: "
                        + Thread.currentThread().getName()
        );

        System.out.println(
                now() + " [AsyncService] 이메일: "
                        + email
        );

        sleep(2000);

        throw new RuntimeException(
                "비동기 이메일 발송 실패"
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