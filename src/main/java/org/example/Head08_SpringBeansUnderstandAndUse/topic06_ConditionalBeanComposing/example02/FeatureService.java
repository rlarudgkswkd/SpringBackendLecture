package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example02;

/**
 * 조건이 충족될 때만 실제로 컨테이너에 등록되는 Bean
 */
public class FeatureService {

    public String getMessage() {
        return "✅ FeatureService 가 활성화되어 있습니다!";
    }

    public void doWork() {
        System.out.println("FeatureService: 기능 실행 중...");
    }
}