package com.codeit.springasyncexceptiondemo.config;

import com.codeit.springasyncexceptiondemo.handler.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig
        implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor =
                new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(10);

        executor.setThreadNamePrefix(
                "Async-"
        );

        executor.setTaskDecorator(
                new MdcTaskDecorator()
        );

        executor.initialize();

        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler
    getAsyncUncaughtExceptionHandler() {

        return new CustomAsyncExceptionHandler();
    }

    @Bean(name = "mdcTaskExecutor")
    public ThreadPoolTaskExecutor
    mdcTaskExecutor() {

        ThreadPoolTaskExecutor executor =
                new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(10);

        executor.setThreadNamePrefix(
                "MDC-Async-"
        );

        executor.setTaskDecorator(
                new MdcTaskDecorator()
        );

        executor.initialize();

        return executor;
    }
}