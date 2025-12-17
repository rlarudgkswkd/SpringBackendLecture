package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example02;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * custom.feature-enabled 값이 true일 때만 matches() 가 true를 리턴하도록 하는 조건 클래스
 */
public class FeatureEnabledCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        // Environment에서 설정값 읽기
        Environment env = context.getEnvironment();
        String enabled = env.getProperty("custom.feature-enabled", "false");

        boolean result = "true".equalsIgnoreCase(enabled);
        System.out.println("[FeatureEnabledCondition] custom.feature-enabled=" + enabled
                + " → matches=" + result);

        return result;
    }
}