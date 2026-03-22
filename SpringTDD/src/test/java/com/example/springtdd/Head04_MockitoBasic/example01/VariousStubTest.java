package com.example.springtdd.Head04_MockitoBasic.example01;

import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VariousStubTest {

    @Test
    @DisplayName("다양한 Mock 동작 설정 방법")
    void variousStubbingPatterns() {

        // Given - Mock 객체 생성
        EmailService mockEmailService = mock(EmailService.class);

        // 특정 값으로 호출시 특정 반환값 설정
        when(mockEmailService.send(eq("success@example.com"), anyString()))
                .thenReturn(true);

        // 특정 값으로 호출시 예외 발생 설정
        when(mockEmailService.send(eq("error@example.com"), anyString()))
                .thenThrow(new IllegalArgumentException("잘못된 이메일"));

        // 여러 번 호출시 다른 반환값 설정
        when(mockEmailService.send(eq("retry@example.com"), anyString()))
                .thenReturn(false)  // 첫 번째 호출시 false
                .thenReturn(true);  // 두 번째 호출시 true

        // When & Then - 각각의 설정된 동작 확인
        assertTrue(mockEmailService.send("success@example.com", "Hello"));

        assertThrows(IllegalArgumentException.class,
                () -> mockEmailService.send("error@example.com", "Hello"));

        assertFalse(mockEmailService.send("retry@example.com", "Hello")); // 첫 번째
        assertTrue(mockEmailService.send("retry@example.com", "Hello"));  // 두 번째
    }
}