package com.codeit.springsecuritysessionbasedarchi.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SessionApiController {

    @GetMapping("/api/session")
    public Map<String, Object> sessionInfo(
            Authentication authentication,
            HttpSession session
    ) {

        return Map.of(
                "username", authentication.getName(),
                "sessionId", session.getId(),
                "principal", authentication.getPrincipal().toString()
        );
    }
}
