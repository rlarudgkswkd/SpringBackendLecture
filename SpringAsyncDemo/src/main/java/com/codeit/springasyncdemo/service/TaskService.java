package com.codeit.springasyncdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ThreadPoolTaskExecutor ioTaskExecutor;
    private final ThreadPoolTaskExecutor cpuTaskExecutor;

    @Async("ioTaskExecutor")
    public void sendExternalApiTask() {

        printExecutorStatus("IO EXECUTOR", ioTaskExecutor);

        String threadName = Thread.currentThread().getName();

        System.out.println(now() + " [IO TASK] 외부 API 호출 시작");
        System.out.println(now() + " [IO TASK] 실행 스레드: " + threadName);

        sleep(3000);

        System.out.println(now() + " [IO TASK] 외부 API 호출 완료");

        printExecutorStatus("IO EXECUTOR", ioTaskExecutor);
    }

    @Async("cpuTaskExecutor")
    public void calculateReportTask() {

        printExecutorStatus("CPU EXECUTOR", cpuTaskExecutor);

        String threadName = Thread.currentThread().getName();

        System.out.println(now() + " [CPU TASK] 리포트 계산 시작");
        System.out.println(now() + " [CPU TASK] 실행 스레드: " + threadName);

        long sum = 0;

        for (long i = 0; i < 500_000_000L; i++) {
            sum += i;
        }

        System.out.println(now() + " [CPU TASK] 리포트 계산 완료: " + sum);

        printExecutorStatus("CPU EXECUTOR", cpuTaskExecutor);
    }

    private synchronized void printExecutorStatus(
            String executorName,
            ThreadPoolTaskExecutor taskExecutor
    ) {

        ThreadPoolExecutor executor =
                taskExecutor.getThreadPoolExecutor();

        System.out.println();
        System.out.println("========== " + executorName + " 상태 ==========");
        System.out.println("CorePoolSize: " + executor.getCorePoolSize());
        System.out.println("MaximumPoolSize: " + executor.getMaximumPoolSize());
        System.out.println("현재 Pool Size: " + executor.getPoolSize());
        System.out.println("활성 스레드 수: " + executor.getActiveCount());
        System.out.println("큐 대기 작업 수: " + executor.getQueue().size());
        System.out.println("완료된 작업 수: " + executor.getCompletedTaskCount());
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