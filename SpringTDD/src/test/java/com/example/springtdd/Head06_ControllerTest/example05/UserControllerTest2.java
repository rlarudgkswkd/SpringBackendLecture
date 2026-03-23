package com.example.springtdd.Head06_ControllerTest.example05;

import com.example.springtdd.Head06_ControllerTest.controller.UserController;
import com.example.springtdd.Head06_ControllerTest.dto.UpdateUserRequest;
import com.example.springtdd.Head06_ControllerTest.dto.UserResponse;
import com.example.springtdd.Head06_ControllerTest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest2 {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private UserService userService;

    @Test
    @WithMockUser(username = "user1", roles = {"USER"})
    @DisplayName("사용자는 자신의 데이터만 수정할 수 있어야 한다")
    void userCanOnlyModifyOwnData() throws Exception {

        // Given
        Long otherUserId = 999L;
        UpdateUserRequest request = new UpdateUserRequest("hacker@example.com", "해커");

        when(userService.updateUser(
                eq(otherUserId),
                org.mockito.ArgumentMatchers.any(UpdateUserRequest.class)
        )).thenThrow(new AccessDeniedException("다른 사용자의 정보는 수정할 수 없습니다"));

        // When & Then
        mockMvc.perform(put("/api/users/{id}", otherUserId)
                        .with(csrf()) // PUT → 반드시 필요
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("다른 사용자의 정보는 수정할 수 없습니다"));
    }

    @Test
    @WithMockUser(username = "owner", roles = {"USER"})
    @DisplayName("리소스 소유자는 자신의 데이터를 수정할 수 있어야 한다")
    void resourceOwnerCanModifyOwnData() throws Exception {

        // Given - 자신의 데이터 수정 요청
        Long ownUserId = 1L;

        UpdateUserRequest request =
                new UpdateUserRequest("newemail@example.com", "새이름");

        UserResponse updatedUser =
                new UserResponse(ownUserId, "newemail@example.com", "새이름");

        when(userService.updateUser(
                eq(ownUserId),
                org.mockito.ArgumentMatchers.any(UpdateUserRequest.class)
        )).thenReturn(updatedUser);

        // When & Then
        mockMvc.perform(put("/api/users/{id}", ownUserId)
                        .with(csrf()) // PUT 요청 → 필수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newemail@example.com"))
                .andExpect(jsonPath("$.name").value("새이름"));

        // verify (객체 비교 안전하게)
        verify(userService).updateUser(
                eq(ownUserId),
                argThat(req ->
                        req.getEmail().equals("newemail@example.com") &&
                                req.getName().equals("새이름")
                )
        );
    }
}
