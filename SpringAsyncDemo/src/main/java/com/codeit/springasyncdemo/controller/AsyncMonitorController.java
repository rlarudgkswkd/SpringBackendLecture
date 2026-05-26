package com.codeit.springasyncdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequiredArgsConstructor
public class AsyncMonitorController {

    private final ThreadPoolTaskExecutor myTaskExecutor;
    private final ThreadPoolTaskExecutor ioTaskExecutor;
    private final ThreadPoolTaskExecutor cpuTaskExecutor;

    @GetMapping("/api/async/executors")
    public Map<String, Object> getExecutorsStatus() {

        return Map.of(
                "myTaskExecutor", getExecutorStatus(myTaskExecutor),
                "ioTaskExecutor", getExecutorStatus(ioTaskExecutor),
                "cpuTaskExecutor", getExecutorStatus(cpuTaskExecutor)
        );
    }

    private Map<String, Object> getExecutorStatus(
            ThreadPoolTaskExecutor taskExecutor
    ) {

        ThreadPoolExecutor executor =
                taskExecutor.getThreadPoolExecutor();

        return Map.of(
                "corePoolSize", executor.getCorePoolSize(),
                "maximumPoolSize", executor.getMaximumPoolSize(),
                "poolSize", executor.getPoolSize(),
                "activeCount", executor.getActiveCount(),
                "queueSize", executor.getQueue().size(),
                "completedTaskCount", executor.getCompletedTaskCount()
        );
    }
}