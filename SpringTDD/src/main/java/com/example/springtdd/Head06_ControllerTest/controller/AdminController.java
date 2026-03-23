package com.example.springtdd.Head06_ControllerTest.controller;

import com.example.springtdd.Head06_ControllerTest.dto.admin.AdminUserResponse;
import com.example.springtdd.Head06_ControllerTest.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 관리자 전용 API
    @GetMapping("/users")
    public List<AdminUserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }
}
