package com.codeit.springsecuritysessionbasedarchi.handler;

import com.codeit.springsecuritysessionbasedarchi.dto.LoginSuccessInfo;
import com.codeit.springsecuritysessionbasedarchi.dto.UserSessionMetadata;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.security.web.savedrequest.SavedRequest;

import org.springframework.stereotype.Component;

import java.io.IOException;

import java.time.LocalDateTime;

import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        HttpSession session =
                request.getSession();

        log.info(
                "로그인 성공 - 사용자: {}, 세션 ID: {}",
                authentication.getName(),
                session.getId()
        );

        // 세션 메타데이터 저장
        session.setAttribute(
                "USER_SESSION_METADATA",

                UserSessionMetadata.builder()
                        .username(authentication.getName())
                        .loginTime(LocalDateTime.now())
                        .build()
        );

        LoginSuccessInfo loginInfo =
                LoginSuccessInfo.builder()
                        .username(authentication.getName())
                        .sessionId(session.getId())
                        .loginTime(LocalDateTime.now())
                        .userAgent(request.getHeader("User-Agent"))
                        .ipAddress(request.getRemoteAddr())
                        .authorities(
                                authentication.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList()
                        )
                        .build();

        if (isAjaxRequest(request)) {

            handleAjaxSuccess(
                    response,
                    loginInfo
            );

        } else {

            handleWebSuccess(
                    request,
                    response
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

    private void handleAjaxSuccess(
            HttpServletResponse response,
            LoginSuccessInfo loginInfo
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
                                "message", "로그인 성공",
                                "username",
                                loginInfo.getUsername(),
                                "sessionId",
                                loginInfo.getSessionId()
                        )
                )
        );
    }

    private void handleWebSuccess(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.sendRedirect("/dashboard");
    }
}