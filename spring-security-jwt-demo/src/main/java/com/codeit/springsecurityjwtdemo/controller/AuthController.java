package com.codeit.springsecurityjwtdemo.controller;

import com.codeit.springsecurityjwtdemo.dto.TokenRefreshResponse;
import com.codeit.springsecurityjwtdemo.security.JwtTokenProvider;
import com.codeit.springsecurityjwtdemo.security.RefreshTokenStore;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final RefreshTokenStore refreshTokenStore;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserDetailsService userDetailsService;

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<?> refresh(
            HttpServletRequest request
    ) {

        String refreshToken =
                getRefreshTokenFromCookie(request);

        if (refreshToken == null) {
            return ResponseEntity
                    .status(401)
                    .body("Refresh Token이 없습니다.");
        }

        String username =
                refreshTokenStore.findUsername(refreshToken)
                        .orElse(null);

        if (username == null) {
            return ResponseEntity
                    .status(401)
                    .body("유효하지 않은 Refresh Token입니다.");
        }

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        username
                );

        String newAccessToken =
                jwtTokenProvider.generateAccessToken(
                        userDetails
                );

        return ResponseEntity.ok(
                TokenRefreshResponse.builder()
                        .success(true)
                        .accessToken(newAccessToken)
                        .tokenType("Bearer")
                        .build()
        );
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String refreshToken =
                getRefreshTokenFromCookie(request);

        if (refreshToken != null) {
            refreshTokenStore.remove(refreshToken);
        }

        Cookie deleteCookie =
                new Cookie("REFRESH_TOKEN", null);

        deleteCookie.setHttpOnly(true);
        deleteCookie.setSecure(false);
        deleteCookie.setPath("/api/auth");
        deleteCookie.setMaxAge(0);

        response.addCookie(deleteCookie);

        return ResponseEntity.ok("로그아웃 완료");
    }

    private String getRefreshTokenFromCookie(
            HttpServletRequest request
    ) {

        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie ->
                        "REFRESH_TOKEN".equals(
                                cookie.getName()
                        )
                )
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
