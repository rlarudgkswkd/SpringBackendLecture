package com.codeit.springasyncdemo.controller;

import com.codeit.springasyncdemo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/api/email/send")
    public Map<String, Object> sendEmail(
            @RequestParam String email
    ) {

        String threadName = Thread.currentThread().getName();

        System.out.println(now() + " [EmailController] 요청 수신");
        System.out.println(now() + " [EmailController] 요청 처리 스레드: " + threadName);

        emailService.sendEmail(email);

        System.out.println(now() + " [EmailController] 응답 반환");

        return Map.of(
                "success", true,
                "message", "이메일 발송 요청이 접수되었습니다.",
                "requestThread", threadName
        );
    }

    private String now() {
        return "[" + LocalTime.now().withNano(0) + "]";
    }
}