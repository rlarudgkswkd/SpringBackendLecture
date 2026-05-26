package com.codeit.springasyncdemo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class EmailService {

    @Async
    public void sendEmail(String email) {

        String threadName = Thread.currentThread().getName();

        System.out.println(now() + " [EmailService] 이메일 발송 시작");
        System.out.println(now() + " [EmailService] 실행 스레드: " + threadName);
        System.out.println(now() + " [EmailService] 수신자: " + email);

        sleep(3000);

        System.out.println(now() + " [EmailService] 이메일 발송 완료");
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
