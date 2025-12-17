package org.example.Head08_SpringBeansUnderstandAndUse.topic07_BeanOrderRegiControl.example02;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

// SecondRunner.java
@Component
@Order(2)  // 두 번째 실행
public class SecondRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== 2번 Runner 실행 ===");
        System.out.println("캐시 워밍업 작업");

        warmUpCache();

        System.out.println("2번 Runner 완료\n");
    }

    private void warmUpCache() {
        // 자주 사용하는 데이터를 캐시에 로드
        System.out.println("- 사용자 정보 캐싱");
        System.out.println("- 설정 정보 캐싱");
        System.out.println("- 메타데이터 캐싱");
    }
}
