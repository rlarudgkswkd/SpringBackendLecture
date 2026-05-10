package com.codeit.springsecurity.customfilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("===== RequestLoggingFilter 요청 =====");
        System.out.println("URI: " + request.getRequestURI());

        filterChain.doFilter(request, response);

        System.out.println("===== RequestLoggingFilter 응답 =====");
    }
}
