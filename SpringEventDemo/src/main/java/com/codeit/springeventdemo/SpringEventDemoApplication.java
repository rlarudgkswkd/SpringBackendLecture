package com.codeit.springeventdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringEventDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                SpringEventDemoApplication.class,
                args
        );
    }
}