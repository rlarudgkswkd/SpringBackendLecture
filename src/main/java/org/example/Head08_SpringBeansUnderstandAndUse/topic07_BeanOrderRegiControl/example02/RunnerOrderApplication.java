package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunnerOrderApplication {

    public static void main(String[] args) {
        System.out.println("=== Application 시작 ===");
        SpringApplication.run(RunnerOrderApplication.class, args);
        System.out.println("=== Application 종료 준비 완료 ===");
    }
}