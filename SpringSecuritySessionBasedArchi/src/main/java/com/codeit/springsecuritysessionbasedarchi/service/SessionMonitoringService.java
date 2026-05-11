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

    private final SessionRegistry sessionRegistry;

    public SessionMonitoringService(
            SessionRegistry sessionRegistry
    ) {
        this.sessionRegistry = sessionRegistry;
    }

    @Scheduled(fixedRate = 30000)
    public void monitorActiveSessions() {

        List<Object> principals =
                sessionRegistry.getAllPrincipals();

        int totalActiveSessions = 0;

        for (Object principal : principals) {

            List<SessionInformation> sessions =
                    sessionRegistry.getAllSessions(
                            principal,
                            false
                    );

            totalActiveSessions += sessions.size();

            log.info(
                    "사용자: {}, 활성 세션 수: {}",
                    principal,
                    sessions.size()
            );
        }

        log.info(
                "전체 활성 세션 수: {}",
                totalActiveSessions
        );
    }
}