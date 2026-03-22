package com.example.head16_springstablehigh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExceptionLoggingService {

    private static final Logger log = LoggerFactory.getLogger(ExceptionLoggingService.class);

    public void runExceptionCase(String type) {

        try {
            if ("business".equals(type)) {
                throw new IllegalArgumentException("잔액 부족");
            } else if ("system".equals(type)) {
                throw new RuntimeException("DB 연결 실패");
            }

        } catch (IllegalArgumentException e) {
            log.warn("비즈니스 예외 발생 - 메시지: {}", e.getMessage());

        } catch (Exception e) {
            log.error("시스템 오류 발생 - 즉시 대응 필요", e);
        }
    }
}
