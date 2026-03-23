package com.example.springtdd.Head06_ControllerTest.example01;

import com.example.springtdd.Head06_ControllerTest.dto.*;
import com.example.springtdd.Head06_ControllerTest.exception.DuplicateEmailException;
import com.example.springtdd.Head06_ControllerTest.exception.UserNotFoundException;
import com.example.springtdd.Head06_ControllerTest.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.containsString;


@WebMvcTest
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private UserService userService;

    @Test
    @WithMockUser //TEST용으로 Mock User 인증 필요
    @DisplayName("사용자 생성 요청이 올바르게 처리되어야 한다")
    void createUserRequestHandling() throws Exception {

        // Given - 요청 데이터와 서비스 응답 설정
        CreateUserRequest request = new CreateUserRequest("test@example.com", "홍길동", 25);
        UserResponse expectedResponse = new UserResponse(1L, "test@example.com", "홍길동");

        when(userService.createUser(Mockito.any(CreateUserRequest.class)))
                .thenReturn(expectedResponse);

        // When & Then - POST 요청 처리 검증
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("홍길동"));

        // 서비스 메서드가 정확한 파라미터로 호출되었는지 검증
        verify(userService).createUser(argThat(req ->
                req.getEmail().equals("test@example.com") &&
                        req.getName().equals("홍길동")
        ));
    }

    @Test
    @DisplayName("쿼리 파라미터가 포함된 사용자 검색 요청 처리")
    @WithMockUser // 🔥 인증 해결
    void searchUsersWithQueryParameters() throws Exception {

        // Given
        List<UserResponse> searchResults = Arrays.asList(
                new UserResponse(1L, "user1@example.com", "사용자1"),
                new UserResponse(2L, "user2@example.com", "사용자2")
        );

        when(userService.searchUsers("홍길동", 20, 30)).thenReturn(searchResults);

        // When & Then
        mockMvc.perform(get("/api/users/search")
                        .param("name", "홍길동")
                        .param("minAge", "20")
                        .param("maxAge", "30")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 🔥 수정
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("사용자1"))
                .andExpect(jsonPath("$[1].name").value("사용자2"));

        // Then
        verify(userService).searchUsers("홍길동", 20, 30);
    }

    @Test
    @DisplayName("유효하지 않은 요청 데이터에 대해 400 에러가 발생해야 한다")
    @WithMockUser
    void invalidRequestDataValidation() throws Exception {

        String invalidRequestJson = """
        {
            "email": "invalid-email-format",
            "name": "",
            "age": -5
        }
        """;

        mockMvc.perform(post("/api/users")
                        .with(csrf()) // 🔥 필수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors[*].field",
                        containsInAnyOrder("email", "name", "age")))
                .andExpect(jsonPath("$.errors[*].message",
                        hasItem(containsString("이메일 형식"))));

        verify(userService, never()).createUser(any());
    }

    @Test
    @DisplayName("커스텀 검증 로직이 올바르게 동작해야 한다")
    @WithMockUser
    void customValidationLogic() throws Exception {

        // Given
        CreateUserRequest request =
                new CreateUserRequest("existing@example.com", "홍길동", 25);

        when(userService.createUser(Mockito.any(CreateUserRequest.class)))
                .thenThrow(new DuplicateEmailException("이미 존재하는 이메일입니다"));

        // When & Then
        mockMvc.perform(post("/api/users")
                        .with(csrf()) // 🔥 필수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("이미 존재하는 이메일입니다"))
                .andExpect(jsonPath("$.errorCode").value("DUPLICATE_EMAIL"));
    }

    @Test
    @DisplayName("사용자 수정 성공시 올바른 응답 형식이 반환되어야 한다")
    @WithMockUser
    void updateUserResponseFormat() throws Exception {

        // Given
        Long userId = 1L;

        UpdateUserRequest request =
                new UpdateUserRequest("updated@example.com", "수정된이름");

        UserResponse updatedUser =
                new UserResponse(userId, "updated@example.com", "수정된이름");

        when(userService.updateUser(eq(userId), Mockito.any(UpdateUserRequest.class)))
                .thenReturn(updatedUser);

        // When & Then
        mockMvc.perform(put("/api/users/{id}", userId)
                        .with(csrf()) // 🔥 필수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Cache-Control", "no-cache"))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.name").value("수정된이름"));
    }

    @Test
    @DisplayName("사용자 삭제 성공시 204 No Content가 반환되어야 한다")
    @WithMockUser
    void deleteUserNoContentResponse() throws Exception {

        // Given
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", userId)
                        .with(csrf())) // 🔥 필수
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        // Then
        verify(userService).deleteUser(userId);
    }

    @Test
    @DisplayName("존재하지 않는 사용자 조회시 404 에러가 발생해야 한다")
    @WithMockUser
    void userNotFoundExceptionHandling() throws Exception {

        // Given
        Long nonExistentUserId = 999L;

        when(userService.getUserById(nonExistentUserId))
                .thenThrow(new UserNotFoundException("사용자를 찾을 수 없습니다"));

        // When & Then
        mockMvc.perform(get("/api/users/{id}", nonExistentUserId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("사용자를 찾을 수 없습니다"))
                .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.path").value("/api/users/999"));
    }

    @Test
    @DisplayName("서버 내부 오류시 500 에러가 발생하고 민감한 정보가 노출되지 않아야 한다")
    @WithMockUser
    void internalServerErrorHandling() throws Exception {

        // Given
        when(userService.createUser(Mockito.any(CreateUserRequest.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        CreateUserRequest request =
                new CreateUserRequest("test@example.com", "홍길동", 25);

        // When & Then
        mockMvc.perform(post("/api/users")
                        .with(csrf()) // 필수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("서버 내부 오류가 발생했습니다"))
                .andExpect(jsonPath("$.errorCode").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.details").doesNotExist());
    }
}
