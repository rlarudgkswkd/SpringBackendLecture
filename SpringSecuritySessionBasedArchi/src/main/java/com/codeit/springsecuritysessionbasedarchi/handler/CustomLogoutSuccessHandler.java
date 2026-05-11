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

    /*
     * JSON 응답 생성을 위한 ObjectMapper
     *
     * AJAX 로그아웃 응답에서 사용된다.
     */
    private final ObjectMapper objectMapper =
            new ObjectMapper();

    /*
     * 현재 활성 세션들을 관리하는 객체
     *
     * Spring Security의 동시 세션 관리 기능에서 사용된다.
     */
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

        /*
         * Authentication이 null일 수 있으므로 방어 코드 작성
         *
         * 예:
         * - 이미 세션이 만료된 경우
         * - 인증 정보가 제거된 상태
         */
        String username =
                authentication != null
                        ? authentication.getName()
                        : "unknown";

        /*
         * 현재 세션 ID 조회
         */
        String sessionId =
                request.getSession().getId();

        log.info(
                "로그아웃 완료 - 사용자: {}, 세션 ID: {}",
                username,
                sessionId
        );

        /*
         * SessionRegistry에서 현재 사용자 세션 조회
         *
         * 동시 로그인 제어 시 사용된다.
         */
        if (authentication != null) {

            List<SessionInformation> sessions =
                    sessionRegistry.getAllSessions(

                            // 현재 인증 사용자
                            authentication.getPrincipal(),

                            // false:
                            // 만료(expired) 세션 제외
                            false
                    );

            /*
             * 현재 세션과 동일한 세션 탐색
             */
            for (SessionInformation sessionInfo : sessions) {

                if (sessionId.equals(
                        sessionInfo.getSessionId()
                )) {

                    /*
                     * 현재 세션 만료 처리
                     *
                     * expireNow() 호출 시
                     * SessionInformation 상태가 만료로 변경된다.
                     */
                    sessionInfo.expireNow();

                    break;
                }
            }
        }

        /*
         * AJAX 요청 여부에 따라 응답 방식 분기
         *
         * - AJAX 요청:
         *   JSON 응답 반환
         *
         * - 일반 브라우저 요청:
         *   페이지 Redirect
         */
        if (isAjaxRequest(request)) {

            handleAjaxLogout(
                    response,
                    username
            );

        } else {

            /*
             * 일반 웹 요청은 로그인 페이지로 이동
             */
            response.sendRedirect(
                    "/login?logout=true"
            );
        }
    }

    /*
     * AJAX 요청 여부 확인
     *
     * fetch/AJAX 요청에서는
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
     * AJAX 로그아웃 응답 처리
     *
     * JSON 형태로 응답 반환
     *
     * SPA / React / Vue 환경에서 자주 사용된다.
     */
    private void handleAjaxLogout(
            HttpServletResponse response,
            String username
    ) throws IOException {

        /*
         * HTTP 200 OK 설정
         */
        response.setStatus(
                HttpServletResponse.SC_OK
        );

        /*
         * JSON 응답 타입 설정
         */
        response.setContentType(
                "application/json;charset=UTF-8"
        );

        /*
         * JSON 응답 생성
         */
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