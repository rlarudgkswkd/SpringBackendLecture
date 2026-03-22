package com.example.springtdd.Head04_MockitoBasic.example01;

import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // JUnit 5에서 Mockito 사용을 위한 확장
class EmailServiceTest {

    @Mock  // 필드 레벨에서 Mock 객체 선언
    private EmailService emailService;

    @Test
    @DisplayName("이메일 전송 시 EmailClient가 호출되어야 한다")
    void whenSendCalled_thenEmailClientShouldBeInvoked() {

        // Given - Mock 객체의 동작 정의
        // emailClient.send()가 호출되면 true를 반환하도록 설정
        when(emailService.send(anyString(), anyString())).thenReturn(true);

        // When - 실제 메서드 호출
        boolean result = emailService.send("test@example.com", "Hello");

        // Then - 결과 검증 및 호출 여부 확인
        assertTrue(result);  // 반환값이 예상대로인지 확인

        // Mock 객체가 정확한 파라미터로 호출되었는지 검증
        verify(emailService).send("test@example.com", "Hello");
    }
}