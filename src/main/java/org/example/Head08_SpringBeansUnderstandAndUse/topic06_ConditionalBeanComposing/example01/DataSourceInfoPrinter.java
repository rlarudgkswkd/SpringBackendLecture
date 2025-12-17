package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example01;

// DataSourceInfoPrinter.java
public interface DataSourceInfoPrinter {

    void printDataSourceInfo();

    // 프로필 식별을 위한 메서드 추가
    default String getProfileName() {
        return "Unknown";
    }

    // 우선순위 설정 (숫자가 높을수록 우선)
    default int getPriority() {
        return 0;
    }
}
