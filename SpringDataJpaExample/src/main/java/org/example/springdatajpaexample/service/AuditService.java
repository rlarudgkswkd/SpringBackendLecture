package org.example.springdatajpaexample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdatajpaexample.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final MenuRepository menuRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void writeAuditMenu(Long categoryId) {
        log.info("[Audit] tx active={}, name={}",
                TransactionSynchronizationManager.isActualTransactionActive(),
                TransactionSynchronizationManager.getCurrentTransactionName());

        // "감사 로그" 대신 DB에 흔적을 남기기 위해 메뉴 1개 저장(카테고리 id로 Menu 생성은 도메인상 Category 필요)
        // 실습 단순화를 위해: 가격 업데이트 같은 DB write로 대체하는 게 안전
        // 여기서는 업데이트 예시로 진행 (실제로는 audit 테이블을 따로 만드는 게 정석)
        menuRepository.updatePrice(1L, 9999);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override public void afterCompletion(int status) {
                log.info("[Audit] END = {}", status == STATUS_COMMITTED ? "COMMIT" : "ROLLBACK");
            }
        });
    }
}