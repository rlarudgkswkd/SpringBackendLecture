package com.codeit.springsecuritysessionmanagementcore.controller;

import com.codeit.springsecuritysessionmanagementcore.dto.AuthenticationInfoResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityContextController {

    @GetMapping("/api/authentication-info")
    public AuthenticationInfoResponse authenticationInfo(
            HttpSession session
    ) {

        // 현재 요청 Thread에 연결된 SecurityContext 조회
        SecurityContext securityContext =
                SecurityContextHolder.getContext();

        // SecurityContext 내부의 Authentication 조회
        Authentication authentication =
                securityContext.getAuthentication();

        return AuthenticationInfoResponse.builder()

                // 현재 인증 사용자 이름
                .username(authentication.getName())

                // Authentication 구현체 타입
                .authenticationClass(
                        authentication.getClass().getSimpleName()
                )

                // Principal 객체 타입
                .principalClass(
                        authentication.getPrincipal()
                                .getClass()
                                .getSimpleName()
                )

                // 인증 여부
                .authenticated(
                        authentication.isAuthenticated()
                )

                // 권한 목록
                .authorities(
                        authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )

                // 현재 세션 ID
                .sessionId(
                        session.getId()
                )

                // 현재 요청을 처리하는 Thread 이름
                .threadName(
                        Thread.currentThread().getName()
                )

                .build();
    }

    /*
     * HttpSession 내부 속성 조회 API
     */
    @GetMapping("/api/session-attributes")
    public Map<String, Object> sessionAttributes(
            HttpSession session
    ) {

        Map<String, Object> attributes = new HashMap<>();
        Enumeration<String> attributeNames = session.getAttributeNames();

        while (attributeNames.hasMoreElements()) {

            String attributeName = attributeNames.nextElement();
            Object attributeValue = session.getAttribute(attributeName);

            attributes.put(
                    attributeName,
                    attributeValue.getClass().getName()
            );
        }

        return attributes;
    }

    @GetMapping("/api/session-info")
    public Map<String, Object> sessionInfo(
            HttpSession session
    ) {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "sessionId",
                session.getId()
        );

        response.put(
                "creationTime",
                session.getCreationTime()
        );

        response.put(
                "lastAccessedTime",
                session.getLastAccessedTime()
        );

        response.put(
                "maxInactiveInterval",
                session.getMaxInactiveInterval()
        );

        return response;
    }
}
