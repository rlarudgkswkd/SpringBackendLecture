package org.example.Head08_SpringBeansUnderstandAndUse.topic05_SettingInfoExternalize.example01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppPropertiesExample {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.description}")
    private String description;

    @Value("${app.max-users}")
    private int maxUsers;

    @Value("${app.enable-cache}")
    private boolean enableCache;

    public void printProperties() {
        System.out.println("=== 외부 설정 값 출력 ===");
        System.out.println("서비스명: " + appName);
        System.out.println("버전: " + appVersion);
        System.out.println("설명: " + description);
        System.out.println("최대 사용자 수: " + maxUsers);
        System.out.println("캐시 활성화 여부: " + enableCache);
    }
}