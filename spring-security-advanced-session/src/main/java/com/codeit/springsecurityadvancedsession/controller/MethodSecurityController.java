package com.codeit.springsecurityadvancedsession.controller;

import com.codeit.springsecurityadvancedsession.service.AdminService;
import com.codeit.springsecurityadvancedsession.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MethodSecurityController {

    private final AdminService adminService;

    private final UserService userService;

    public MethodSecurityController(
            AdminService adminService,
            UserService userService
    ) {

        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/method/admin")
    public String adminMethod() {

        return adminService.getAdminData();
    }

    @GetMapping("/method/user")
    public String userMethod() {

        return userService.getUserData();
    }
}
