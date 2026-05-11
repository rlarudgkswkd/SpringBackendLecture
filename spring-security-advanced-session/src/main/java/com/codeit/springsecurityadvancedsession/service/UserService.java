package com.codeit.springsecurityadvancedsession.service;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    /*
     * USER 권한 이상 접근 가능
     *
     * RoleHierarchy 적용 상태이므로
     * ADMIN 도 접근 가능하다.
     */
    @PreAuthorize("hasRole('USER')")
    public String getUserData() {

        return """
                사용자 데이터 조회 성공
                """;
    }
}
