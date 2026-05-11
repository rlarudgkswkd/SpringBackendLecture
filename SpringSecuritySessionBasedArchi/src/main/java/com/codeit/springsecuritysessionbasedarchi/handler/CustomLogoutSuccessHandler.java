package com.codeit.springsecuritysessionbasedarchi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CustomLogoutSuccessHandler
        implements LogoutSuccessHandler {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    private final SessionRegistry sessionRegistry;

    public CustomLogoutSuccessHandler(
            SessionRegistry sessionRegistry
    ) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        String username =
                authentication != null
                        ? authentication.getName()
                        : "unknown";

        String sessionId =
                request.getSession().getId();

        log.info(
                "로그아웃 완료 - 사용자: {}, 세션 ID: {}",
                username,
                sessionId
        );

        if (authentication != null) {

            List<SessionInformation> sessions =
                    sessionRegistry.getAllSessions(
                            authentication.getPrincipal(),
                            false
                    );

            for (SessionInformation sessionInfo : sessions) {

                if (sessionId.equals(
                        sessionInfo.getSessionId()
                )) {

                    sessionInfo.expireNow();
                    break;
                }
            }
        }

        if (isAjaxRequest(request)) {

            handleAjaxLogout(
                    response,
                    username
            );

        } else {

            response.sendRedirect(
                    "/login?logout=true"
            );
        }
    }

    private boolean isAjaxRequest(
            HttpServletRequest request
    ) {

        return "XMLHttpRequest".equals(
                request.getHeader("X-Requested-With")
        );
    }

    private void handleAjaxLogout(
            HttpServletResponse response,
            String username
    ) throws IOException {

        response.setStatus(
                HttpServletResponse.SC_OK
        );

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        response.getWriter().write(
                objectMapper.writeValueAsString(
                        Map.of(
                                "success", true,
                                "message", "로그아웃되었습니다.",
                                "username", username,
                                "timestamp",
                                LocalDateTime.now().toString()
                        )
                )
        );
    }
}