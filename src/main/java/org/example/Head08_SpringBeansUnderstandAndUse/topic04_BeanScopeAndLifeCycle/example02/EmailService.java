package org.example.Head08_SpringBeansUnderstandAndUse.topic04_BeanScopeAndLifeCycle.example02;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    private EmailClient emailClient;
    private boolean isConnected = false;

    @PostConstruct
    public void initialize() {
        System.out.println("EmailService 초기화 시작");

        emailClient = new EmailClient();

        try {
            emailClient.connect();
            isConnected = true;
            System.out.println("이메일 서버 연결 성공");
        } catch (Exception e) {
            System.err.println("이메일 서버 연결 실패: " + e.getMessage());
        }

        System.out.println("EmailService 초기화 완료");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("EmailService 종료 작업 시작");

        if (emailClient != null && isConnected) {
            emailClient.disconnect();
            System.out.println("이메일 서버 연결 종료");
        }

        System.out.println("EmailService 종료 작업 완료");
    }

    public void sendEmail(String to, String subject, String body) {
        if (!isConnected) {
            throw new IllegalStateException("이메일 서비스가 초기화되지 않았습니다");
        }
        System.out.println("이메일 발송 → " + to + " | 제목: " + subject);
    }
}
