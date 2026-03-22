package com.example.head16_springstablehigh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BadLoggingService {

    private static final Logger log = LoggerFactory.getLogger(BadLoggingService.class);

    public void runBadExample() {
        log.info("처리 완료");
        log.error("오류 발생");
    }
}
