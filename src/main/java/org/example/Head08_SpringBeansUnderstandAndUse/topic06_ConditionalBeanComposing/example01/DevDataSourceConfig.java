package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

// DevDataSourceConfig.java
@Configuration
@Profile("dev")
public class DevDataSourceConfig {

    @Bean("devDataSourceInfoPrinter")  // Bean 이름 명시적 지정
    @Primary  // 같은 타입의 Bean이 여러 개일 때 우선 선택
    public DataSourceInfoPrinter dataSourceInfoPrinter() {

        return new DataSourceInfoPrinter() {
            @Override
            public void printDataSourceInfo() {
                System.out.println("[DEV] 개발용 데이터소스 적용됨");
                System.out.println("- H2 인메모리 DB 사용");
                System.out.println("- 자동 스키마 생성 활성화");
            }

            @Override
            public String getProfileName() {
                return "DEVELOPMENT";
            }

            @Override
            public int getPriority() {
                return 10;  // 개발 환경 우선순위
            }
        };
    }
}
