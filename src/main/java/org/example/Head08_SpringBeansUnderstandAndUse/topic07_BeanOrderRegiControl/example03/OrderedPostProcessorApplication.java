package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderedPostProcessorApplication {

    public static void main(String[] args) {
        System.out.println("=== Application 시작 ===");
        SpringApplication.run(OrderedPostProcessorApplication.class, args);
        System.out.println("=== Application 컨텍스트 초기화 완료 ===");
    }
}