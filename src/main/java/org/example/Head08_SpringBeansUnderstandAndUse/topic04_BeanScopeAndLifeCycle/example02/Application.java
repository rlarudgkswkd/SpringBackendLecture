package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n=== EmailService 사용 ===");
        EmailService emailService = context.getBean(EmailService.class);
        emailService.sendEmail("test@example.com", "안녕하세요", "본문 내용 예시");

        System.out.println("\n=== 컨테이너 종료 ===");
        context.close();
    }
}
