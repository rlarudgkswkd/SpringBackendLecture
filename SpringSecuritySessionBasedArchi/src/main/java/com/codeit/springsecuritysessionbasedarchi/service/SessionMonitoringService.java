package com.codeit.springsecuritysessionbasedarchi.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SessionMonitoringService {

    // 현재 활성 세션들을 관리하는 객체
    // Spring Security의 동시 세션 관리 기능에서 사용된다.
    private final SessionRegistry sessionRegistry;

    public SessionMonitoringService(
            SessionRegistry sessionRegistry
    ) {
        this.sessionRegistry = sessionRegistry;
    }

    // 30초마다 실행되는 스케줄링 메서드
    //
    // fixedRate = 30000
    // → 이전 실행 시작 기준 30초마다 반복 실행
    @Scheduled(fixedRate = 30000)
    public void monitorActiveSessions() {

        // 현재 SessionRegistry에 등록된 사용자 목록 조회
        List<Object> principals =
                sessionRegistry.getAllPrincipals();

        // 전체 활성 세션 수 저장 변수
        int totalActiveSessions = 0;

        // 사용자별 활성 세션 조회
        for (Object principal : principals) {

            // 현재 사용자의 활성 세션 목록 조회
            //
            // false:
            // 만료(expired) 세션 제외
            List<SessionInformation> sessions =
                    sessionRegistry.getAllSessions(
                            principal,
                            false
                    );

            // 전체 활성 세션 수 누적
            totalActiveSessions += sessions.size();

            // 사용자별 세션 상태 출력
            log.info(
                    "사용자: {}, 활성 세션 수: {}",
                    principal,
                    sessions.size()
            );
        }

        // 전체 활성 세션 수 출력
        log.info(
                "전체 활성 세션 수: {}",
                totalActiveSessions
        );
    }
}