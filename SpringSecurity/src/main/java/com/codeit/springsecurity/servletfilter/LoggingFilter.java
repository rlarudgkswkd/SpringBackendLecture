package com.codeit.springsecurity.servletfilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        System.out.println("===== LoggingFilter 시작 =====");
        System.out.println("URI: " + httpRequest.getRequestURI());

        chain.doFilter(request, response);

        System.out.println("===== LoggingFilter 종료 =====");
    }
}