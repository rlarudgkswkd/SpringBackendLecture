package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 애플리케이션 시작 후 자동으로 실행되며
 * FeatureService Bean 이 있는지 여부를 확인하는 클래스
 */
@Component
public class FeatureRunner implements CommandLineRunner {

    private final FeatureService featureService; // null 일 수도 있음

    // Optional + required = false 조합으로 “있으면 쓰고, 없으면 넘어가는” 패턴
    @Autowired
    public FeatureRunner(Optional<FeatureService> featureService) {
        this.featureService = featureService.orElse(null);
    }

    @Override
    public void run(String... args) {
        System.out.println("====================================");
        System.out.println("  @Conditional 실습 결과 확인");
        System.out.println("====================================");

        if (featureService != null) {
            System.out.println(featureService.getMessage());
            featureService.doWork();
        } else {
            System.out.println("❌ FeatureService Bean 이 등록되지 않았습니다.");
            System.out.println("   (custom.feature-enabled=false 인 경우)");
        }
    }
}