package com.example.head16_springstablehigh.controller;

import com.example.head16_springstablehigh.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestParam String email) {
        userService.register(email);
        return "회원가입 성공";
    }
}
