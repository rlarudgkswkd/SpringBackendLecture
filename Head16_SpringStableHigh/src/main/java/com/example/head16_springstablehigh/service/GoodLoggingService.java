package com.example.head16_springstablehigh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GoodLoggingService {

    private static final Logger log = LoggerFactory.getLogger(GoodLoggingService.class);

    public void runGoodExample() {

        String orderId = "ORD-123";
        String userId = "USER-1";
        int amount = 10000;

        log.info("주문 처리 완료 - 주문ID: {}, 사용자: {}, 금액: {}", orderId, userId, amount);

        try {
            throw new RuntimeException("결제 실패");
        } catch (Exception e) {
            log.error("결제 처리 실패 - 주문ID: {}, 사용자: {}, 오류: {}", orderId, userId, e.getMessage(), e);
        }
    }
}
