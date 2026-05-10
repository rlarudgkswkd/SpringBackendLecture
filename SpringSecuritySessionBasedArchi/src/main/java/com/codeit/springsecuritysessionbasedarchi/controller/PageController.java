package com.codeit.springsecuritysessionbasedarchi.controller;

import com.codeit.springsecuritysessionbasedarchi.dto.SessionUserInfo;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(
            Authentication authentication,
            HttpSession session,
            Model model
    ) {

        Object securityContext =
                session.getAttribute("CUSTOM_SECURITY_CONTEXT");

        log.info("세션 ID = {}", session.getId());

        log.info("SecurityContext = {}", securityContext);

        SessionUserInfo sessionUserInfo =
                SessionUserInfo.builder()
                        .username(authentication.getName())
                        .sessionId(session.getId())
                        .authenticationClass(
                                authentication.getClass().getSimpleName()
                        )
                        .build();

        model.addAttribute(
                "sessionUserInfo",
                sessionUserInfo
        );

        model.addAttribute(
                "securityContext",
                securityContext
        );

        return "dashboard";
    }
}
