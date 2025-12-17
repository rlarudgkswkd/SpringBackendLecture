package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example02;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// ThirdRunner.java - Order 없는 경우
@Component
// @Order 없음 → 기본값: Integer.MAX_VALUE (가장 나중에 실행)
public class ThirdRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== 기본 우선순위 Runner 실행 ===");
        System.out.println("마지막으로 실행됨 (Order 지정 안함)");
        System.out.println("애플리케이션 준비 완료!\n");
    }
}
