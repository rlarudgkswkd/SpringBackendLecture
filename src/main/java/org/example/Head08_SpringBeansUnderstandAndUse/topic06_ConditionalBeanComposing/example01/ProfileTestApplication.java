package org.example.Head08_SpringBeansUnderstandAndUse.topic06_ConditionalBeanComposing.example01;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// ProfileTestApplication.java
@SpringBootApplication
public class ProfileTestApplication {

    // 모든 DataSourceInfoPrinter 구현체를 리스트로 주입
    private final List<DataSourceInfoPrinter> printers;

    @Autowired
    private Environment environment;

    public ProfileTestApplication(List<DataSourceInfoPrinter> printers) {
        this.printers = printers;
    }

    @PostConstruct
    public void init() {
        System.out.println("====================================");
        System.out.println("활성 프로필: " + Arrays.toString(environment.getActiveProfiles()));
        System.out.println("====================================");

        // 우선순위에 따라 정렬
        List<DataSourceInfoPrinter> sortedPrinters = printers.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getPriority(), p1.getPriority()))
                .collect(Collectors.toList());

        // 모든 활성화된 DataSource 정보 출력
        sortedPrinters.forEach(printer -> {
            System.out.println("\n[Profile: " + printer.getProfileName() +
                    ", Priority: " + printer.getPriority() + "]");
            printer.printDataSourceInfo();
        });

        // 가장 높은 우선순위의 DataSource 사용
        if (!sortedPrinters.isEmpty()) {
            DataSourceInfoPrinter primary = sortedPrinters.get(0);
            System.out.println("\n>>> 최종 선택된 DataSource: " + primary.getProfileName());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ProfileTestApplication.class, args);
    }
}

