package com.codeit.springasyncdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final ThreadPoolTaskExecutor myTaskExecutor;

    @Async("myTaskExecutor")
    public void sendEmail(String email) {

        printExecutorStatus();

        String threadName = Thread.currentThread().getName();

        System.out.println(now() + " [EmailService] 이메일 발송 시작");
        System.out.println(now() + " [EmailService] 실행 스레드: " + threadName);
        System.out.println(now() + " [EmailService] 수신자: " + email);

        sleep(3000);

        System.out.println(now() + " [EmailService] 이메일 발송 완료");

        printExecutorStatus();
    }

    private void printExecutorStatus() {

        ThreadPoolExecutor executor =
                myTaskExecutor.getThreadPoolExecutor();

        System.out.println();
        System.out.println("========== Executor 상태 ==========");

        System.out.println(
                "CorePoolSize: "
                        + executor.getCorePoolSize()
        );

        System.out.println(
                "MaximumPoolSize: "
                        + executor.getMaximumPoolSize()
        );

        System.out.println(
                "현재 Pool Size: "
                        + executor.getPoolSize()
        );

        System.out.println(
                "활성 스레드 수: "
                        + executor.getActiveCount()
        );

        System.out.println(
                "큐 대기 작업 수: "
                        + executor.getQueue().size()
        );

        System.out.println(
                "완료된 작업 수: "
                        + executor.getCompletedTaskCount()
        );

        System.out.println("==================================");
        System.out.println();
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