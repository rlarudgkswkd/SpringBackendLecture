package com.codeit.springasyncexceptiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringAsyncExceptionDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                SpringAsyncExceptionDemoApplication.class,
                args
        );
    }
}