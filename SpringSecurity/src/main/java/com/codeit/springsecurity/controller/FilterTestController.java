package com.codeit.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterTestController {

    @GetMapping("/public/filter-test")
    public String test() throws InterruptedException {

        Thread.sleep(300);

        return "filter test success";
    }
}
