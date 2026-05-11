package com.codeit.springsecurityadvancedsession.service;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    /*
     * ADMIN 권한이 있어야 실행 가능
     */
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminData() {

        return """
                관리자 전용 데이터 조회 성공
                """;
    }
}
