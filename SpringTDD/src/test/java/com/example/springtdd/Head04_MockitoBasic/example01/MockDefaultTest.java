package com.example.springtdd.Head04_MockitoBasic.example01;

import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MockDefaultTest {

    @Test
    @DisplayName("Mock 객체의 기본 반환값 확인")
    void mockDefaultBehavior() {

        // Given - Mock 객체 생성
        EmailService mockEmailService = mock(EmailService.class);

        // When - 아무런 설정 없이 메서드 호출
        boolean result = mockEmailService.send("test@example.com", "Hello");
        String response = mockEmailService.getResponse();
        int count = mockEmailService.getCount();

        // Then - Mock 객체의 기본 반환값 확인
        assertFalse(result);        // boolean은 false
        assertNull(response);       // 객체는 null
        assertEquals(0, count);     // 숫자는 0
    }
}
