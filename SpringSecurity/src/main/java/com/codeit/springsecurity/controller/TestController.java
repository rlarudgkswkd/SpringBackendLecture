package com.codeit.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/hello")
    public String publicHello() {
        System.out.println("/public/hello api 실행");
        return "공개 페이지";
    }

    @GetMapping("/private/hello")
    public String privateHello() {
        System.out.println("/private/hello api 실행");
        return "인증 필요 페이지";
    }
}
