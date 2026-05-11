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

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    // JSON 응답 생성을 위한 ObjectMapper
    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        // 세션이 존재하지 않으면 새로 생성
        HttpSession session =
                request.getSession();

        log.info(
                "로그인 성공 - 사용자: {}, 세션 ID: {}",
                authentication.getName(),
                session.getId()
        );

        /*
         * 사용자 세션 메타데이터 저장
         *
         * HttpSession 내부에 사용자 관련 데이터를 저장할 수 있다.
         * Spring Security의 SecurityContext와는 별개의 사용자 정의 데이터이다.
         */
        session.setAttribute(
                "USER_SESSION_METADATA",

                UserSessionMetadata.builder()
                        .username(authentication.getName())
                        .loginTime(LocalDateTime.now())
                        .build()
        );

        /*
         * 로그인 성공 응답 DTO 생성
         *
         * AJAX 요청일 경우 JSON 응답으로 반환된다.
         */
        LoginSuccessInfo loginInfo =
                LoginSuccessInfo.builder()
                        .username(authentication.getName())
                        .sessionId(session.getId())
                        .loginTime(LocalDateTime.now())
                        .userAgent(request.getHeader("User-Agent"))
                        .ipAddress(request.getRemoteAddr())

                        // 권한 목록 추출
                        .authorities(
                                authentication.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList()
                        )
                        .build();

        // AJAX 요청 여부 확인
        if (isAjaxRequest(request)) {

            handleAjaxSuccess(
                    response,
                    loginInfo
            );

        } else {

            // 일반 브라우저 요청이면 페이지 이동
            handleWebSuccess(
                    request,
                    response
            );
        }
    }

    /*
     * AJAX 요청 여부 판단
     *
     * 브라우저 fetch/AJAX 요청에서는
     * X-Requested-With 헤더를 통해 구분 가능하다.
     */
    private boolean isAjaxRequest(
            HttpServletRequest request
    ) {

        return "XMLHttpRequest".equals(
                request.getHeader("X-Requested-With")
        );
    }

    /*
     * AJAX 로그인 성공 응답 처리
     *
     * JSON 형태로 응답 반환
     */
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

    /*
     * 일반 웹 로그인 성공 처리
     *
     * 브라우저 페이지 이동 수행
     */
    private void handleWebSuccess(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.sendRedirect("/dashboard");
    }
}