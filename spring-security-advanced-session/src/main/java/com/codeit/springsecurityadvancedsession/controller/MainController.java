package com.codeit.springsecurityadvancedsession.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/home")
    public String home(
            Authentication authentication,
            Model model
    ) {

        model.addAttribute(
                "username",
                authentication.getName()
        );

        return "home";
    }

    @GetMapping("/user/profile")
    @ResponseBody
    public String userPage(
            Authentication authentication
    ) {

        return """
            USER 페이지 접근 성공

            username: %s
            authorities: %s
            """
                .formatted(
                        authentication.getName(),
                        authentication.getAuthorities()
                );
    }

    @GetMapping("/admin/dashboard")
    @ResponseBody
    public String adminPage(
            Authentication authentication
    ) {

        return """
            ADMIN 페이지 접근 성공

            username: %s
            authorities: %s
            """
                .formatted(
                        authentication.getName(),
                        authentication.getAuthorities()
                );
    }
}