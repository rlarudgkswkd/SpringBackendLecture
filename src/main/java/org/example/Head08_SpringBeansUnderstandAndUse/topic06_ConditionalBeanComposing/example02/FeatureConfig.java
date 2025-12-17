package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * FeatureService Bean을
 * FeatureEnabledCondition 이 true일 때만 등록한다.
 */
@Configuration
public class FeatureConfig {

    @Bean
    @Conditional(FeatureEnabledCondition.class)
    public FeatureService featureService() {
        System.out.println("[FeatureConfig] FeatureService Bean 생성");
        return new FeatureService();
    }
}