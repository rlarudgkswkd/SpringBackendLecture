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

    @GetMapping("/api/users/register")
    public Map<String, Object> register() {

        System.out.println(
                now() + " [UserController] 회원가입 요청"
        );

        userService.registerUser(
                "event@test.com"
        );

        return Map.of(
                "success", true,
                "message", "회원가입 완료"
        );
    }

    private String now() {
        return "[" + LocalTime.now().withNano(0) + "]";
    }
}