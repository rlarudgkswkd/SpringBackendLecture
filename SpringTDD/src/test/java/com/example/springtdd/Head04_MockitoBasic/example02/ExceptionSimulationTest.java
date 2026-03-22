package com.example.springtdd.Head04_MockitoBasic.example02;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExceptionSimulationTest {

    @Test
    @DisplayName("예외 상황 시뮬레이션")
    void exceptionSimulation() {

        // ✅ Mock 객체 생성 (메서드 내부에서는 mock() 사용해야 함)
        UserNewRepository userRepository = mock(UserNewRepository.class);

        // Given - 잘못된 사용자
        UserNew invalidUser = UserNew.builder()
                .email("")
                .name("")
                .status("ACTIVE")
                .build();

        // 정상 사용자
        UserNew validUser = UserNew.builder()
                .email("valid@example.com")
                .name("정상사용자")
                .status("ACTIVE")
                .build();

        // 특정 조건에서 예외 발생
        when(userRepository.save(invalidUser))
                .thenThrow(new IllegalArgumentException("유효하지 않은 사용자 정보"));

        // 정상 케이스
        when(userRepository.save(validUser))
                .thenReturn(validUser);

        // When & Then

        // ❌ 예외 발생 검증
        assertThrows(IllegalArgumentException.class, () -> {
            userRepository.save(invalidUser);
        });

        // ✅ 정상 동작 검증
        assertDoesNotThrow(() -> {
            UserNew saved = userRepository.save(validUser);
            assertEquals("valid@example.com", saved.getEmail());
        });
    }

    @Test
    @DisplayName("동적인 반환값 생성 - thenAnswer 활용")
    void dynamicReturnWithThenAnswer() {

        EmailService emailService = mock(EmailService.class);

        // Given - 입력 파라미터에 따라 동적으로 반환값 생성
        when(emailService.send(anyString(), anyString()))
                .thenAnswer(invocation -> {

                    // 메서드 호출시 전달된 인자들을 가져옴
                    String email = invocation.getArgument(0);
                    String message = invocation.getArgument(1);

                    // 이메일 형식이 올바르고 메시지가 비어있지 않으면 true 반환
                    return email.contains("@") && !message.trim().isEmpty();
                });

        // When & Then - 입력값에 따른 동적 반환값 확인
        assertTrue(emailService.send("valid@example.com", "Hello"));  // 유효한 경우
        assertFalse(emailService.send("invalid-email", "Hello"));     // 잘못된 이메일
        assertFalse(emailService.send("valid@example.com", ""));      // 빈 메시지
        assertFalse(emailService.send("invalid", "   "));             // 둘 다 잘못된 경우
    }
}