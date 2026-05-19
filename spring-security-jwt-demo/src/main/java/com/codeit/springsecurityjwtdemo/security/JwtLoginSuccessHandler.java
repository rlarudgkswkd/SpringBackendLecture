package com.codeit.springsecurityjwtdemo.security;

import com.codeit.springsecurityjwtdemo.dto.LoginSuccessResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtLoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtTokenProvider
            jwtTokenProvider;

    private final ObjectMapper
            objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        UserDetails userDetails =
                (UserDetails)
                        authentication.getPrincipal();

        String accessToken =
                jwtTokenProvider.generateAccessToken(
                        userDetails
                );

        LoginSuccessResponse responseBody =
                LoginSuccessResponse.builder()

                        .success(true)

                        .accessToken(accessToken)

                        .tokenType("Bearer")

                        .username(
                                userDetails.getUsername()
                        )

                        .build();

        response.setStatus(
                HttpServletResponse.SC_OK
        );

        response.setContentType(
                MediaType.APPLICATION_JSON_VALUE
        );

        response.setCharacterEncoding(
                "UTF-8"
        );

        objectMapper.writeValue(
                response.getWriter(),
                responseBody
        );

        log.info(
                "JWT login success: {}",
                userDetails.getUsername()
        );
    }
}
