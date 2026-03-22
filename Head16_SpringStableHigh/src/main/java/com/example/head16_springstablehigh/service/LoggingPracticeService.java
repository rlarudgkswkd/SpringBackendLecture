package com.example.head16_springstablehigh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingPracticeService {

    private static final Logger log = LoggerFactory.getLogger(LoggingPracticeService.class);

    public void runBasicLogs() {

        log.error("ERROR 로그 - 시스템 장애 상황");
        log.warn("WARN 로그 - 재고 부족 상황");
        log.info("INFO 로그 - 주문 생성 완료");
        log.debug("DEBUG 로그 - 내부 변수 값: count=10");
        log.trace("TRACE 로그 - 매우 상세한 흐름");
    }
}
