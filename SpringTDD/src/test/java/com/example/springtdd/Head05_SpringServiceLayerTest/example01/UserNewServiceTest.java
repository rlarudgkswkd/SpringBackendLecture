package com.example.springtdd.Head05_SpringServiceLayerTest.example01;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import com.example.springtdd.Head04_MockitoBasic.service.AuditService;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import com.example.springtdd.Head04_MockitoBasic.service.PasswordEncoder;
import com.example.springtdd.Head04_MockitoBasic.service.UserNewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserNewServiceTest {

    @Mock private UserNewRepository userNewRepository;
    @Mock private EmailService emailService;
    @Mock private AuditService auditService;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserNewService userNewService;

    @Test
    @DisplayName("회원가입시 비밀번호 암호화와 이메일 전송이 수행되어야 한다")
    void userRegistrationProcess() {

        // Given
        UserNew user = UserNew.builder()
                .email("test@test.com")
                .name("홍길동")
                .password("rawPassword")
                .status("ACTIVE")
                .build();

        String encodedPassword = "encodedPassword123";

        when(passwordEncoder.encode("rawPassword")).thenReturn(encodedPassword);
        when(userNewRepository.save(any(UserNew.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(emailService.send("test@test.com", "가입을 환영합니다!")).thenReturn(true);

        // When
        boolean result = userNewService.register(user);

        // Then
        assertTrue(result);

        // 🔥 비밀번호 암호화 검증
        verify(passwordEncoder).encode("rawPassword");

        // 🔥 save 시 password가 암호화 되었는지 검증 (핵심)
        verify(userNewRepository).save(argThat(savedUser ->
                savedUser.getPassword().equals(encodedPassword)
        ));

        // 🔥 이메일 발송 검증
        verify(emailService).send("test@test.com", "가입을 환영합니다!");

        // 🔥 성공 케이스에서는 audit 호출 안됨 (중요 포인트)
        verify(auditService, never()).logFailedRegistration(anyString());
    }
}