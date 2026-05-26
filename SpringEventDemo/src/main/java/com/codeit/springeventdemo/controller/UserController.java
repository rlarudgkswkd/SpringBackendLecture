package com.codeit.springeventdemo.controller;

import com.codeit.springeventdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users/success")
    public Map<String, Object> success() {

        System.out.println(
                now() + " [UserController] 성공 요청"
        );

        userService.registerSuccessUser(
                "success@test.com"
        );

        return Map.of(
                "success", true
        );
    }

    @GetMapping("/api/users/fail")
    public Map<String, Object> fail() {

        System.out.println(
                now() + " [UserController] 실패 요청"
        );

        userService.registerFailUser(
                "fail@test.com"
        );

        return Map.of(
                "success", false
        );
    }

    private String now() {
        return "[" + LocalTime.now().withNano(0) + "]";
    }
}