package com.example.springtdd.Head06_ControllerTest.example04;

import com.example.springtdd.Head06_ControllerTest.controller.AdminController;
import com.example.springtdd.Head06_ControllerTest.dto.AdminUserResponse;
import com.example.springtdd.Head06_ControllerTest.dto.UpdateUserRequest;
import com.example.springtdd.Head06_ControllerTest.dto.UserRole;
import com.example.springtdd.Head06_ControllerTest.service.AdminService;

import com.example.springtdd.Head06_ControllerTest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Security 설정이 있다면 반드시 import 필요
import com.example.springtdd.Head06_ControllerTest.config.SecurityConfig;

// MockMvc 요청 (PUT)
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

// JSON Content-Type
import org.springframework.http.MediaType;

// CSRF 처리
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(AdminController.class)
@Import(SecurityConfig.class)
class AdminControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private AdminService adminService;
    @MockBean private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    /**
     * 일반 사용자는 관리자 API에 접근할 수 없어야 한다
     */
    @Test
    @WithMockUser(username = "normaluser", roles = {"USER"})
    @DisplayName("일반 사용자는 관리자 기능에 접근할 수 없어야 한다")
    void userCannotAccessAdminFunction() throws Exception {

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isForbidden());

        // 서비스가 호출되면 안됨 (Controller까지 못 들어옴)
        verify(adminService, never()).getAllUsers();
    }

    /**
     * 관리자는 관리자 API에 접근 가능해야 한다
     */
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("관리자는 사용자 목록을 조회할 수 있어야 한다")
    void adminCanAccessUserList() throws Exception {

        // Given - 서비스 응답 Mock 설정
        List<AdminUserResponse> users = Arrays.asList(
                new AdminUserResponse(1L, "user1", "user1@example.com", UserRole.USER, true),
                new AdminUserResponse(2L, "user2", "user2@example.com", UserRole.USER, false)
        );

        when(adminService.getAllUsers()).thenReturn(users);

        // When & Then
        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[1].username").value("user2"))
                .andExpect(jsonPath("$[1].active").value(false));

        verify(adminService).getAllUsers();
    }
}