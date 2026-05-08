package com.codeit.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "홈 페이지";
    }

    @GetMapping("/admin")
    public String admin() {
        return "관리자 페이지";
    }
}
