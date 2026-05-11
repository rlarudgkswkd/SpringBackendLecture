package com.codeit.springsecurityadvancedsession.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}