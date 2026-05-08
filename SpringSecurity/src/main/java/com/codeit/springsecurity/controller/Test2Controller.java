package com.codeit.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test2Controller {

    @GetMapping("/public/test")
    public String test() {

        return "success";
    }
}