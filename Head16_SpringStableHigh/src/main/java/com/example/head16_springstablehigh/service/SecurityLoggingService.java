package com.example.head16_springstablehigh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SecurityLoggingService {

    private static final Logger log = LoggerFactory.getLogger(SecurityLoggingService.class);

    public void logUser(String email, String password) {

        String maskedPassword = password.substring(0, 2) + "***";

        log.info("사용자 로그인 - email: {}, password: {}", email, maskedPassword);
    }
}
