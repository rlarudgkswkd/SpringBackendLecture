package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example01;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

// ProdDataSourceConfig.java
@Configuration
@Profile("prod")
@Order(1)  // 초기화 순서 지정, 추후 배울 예정
public class ProdDataSourceConfig {

    @Bean("prodDataSourceInfoPrinter")  // Bean 이름 명시적 지정
    public DataSourceInfoPrinter dataSourceInfoPrinter() {

        return new DataSourceInfoPrinter() {
            @Override
            public void printDataSourceInfo() {
                System.out.println("[PROD] 운영용 데이터소스 적용됨");
                System.out.println("- PostgreSQL 클러스터 연결");
                System.out.println("- Read/Write 분리 활성화");
                System.out.println("- Connection Pool 최적화");
            }

            @Override
            public String getProfileName() {
                return "PRODUCTION";
            }

            @Override
            public int getPriority() {
                return 100;  // 운영 환경 최우선
            }
        };
    }

    // 운영 환경에서만 활성화되는 모니터링 Bean
    @Bean
    public PerformanceMonitor performanceMonitor() {
        return new PerformanceMonitor();
    }
}
