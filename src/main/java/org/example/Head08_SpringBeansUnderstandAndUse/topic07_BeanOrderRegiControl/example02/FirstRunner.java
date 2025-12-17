package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example02;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// FirstRunner.java
@Component
@Order(1)  // 가장 먼저 실행 (숫자가 낮을수록 우선순위 높음)
public class FirstRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== 1번 Runner 실행 ===");
        System.out.println("데이터베이스 초기화 작업");

        // 실제 초기화 로직
        initializeDatabase();

        System.out.println("1번 Runner 완료\n");
    }

    private void initializeDatabase() {
        // 스키마 생성, 초기 데이터 삽입 등
        System.out.println("- 테이블 생성");
        System.out.println("- 인덱스 생성");
        System.out.println("- 초기 데이터 삽입");
    }
}
