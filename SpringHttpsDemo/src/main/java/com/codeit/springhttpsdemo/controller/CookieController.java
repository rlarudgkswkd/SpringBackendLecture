package com.codeit.springhttpsdemo.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CookieController {

    @GetMapping("/api/cookie")
    public String createCookie(
            HttpServletResponse response
    ) {

        ResponseCookie cookie =
                ResponseCookie.from(
                                "sessionId",
                                "abc123"
                        )
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("Strict")
                        .path("/")
                        .build();

        response.addHeader(
                "Set-Cookie",
                cookie.toString()
        );

        return "Secure Cookie Created";
    }
}