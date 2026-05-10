package com.codeit.springsecurity.customfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuditLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("===== AuditLoggingFilter 요청 =====");

        filterChain.doFilter(request, response);

        System.out.println("===== AuditLoggingFilter 응답 =====");
        System.out.println("응답 상태 코드: " + response.getStatus());
    }
}