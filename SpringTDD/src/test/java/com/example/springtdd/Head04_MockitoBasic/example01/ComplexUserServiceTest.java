package com.example.springtdd.Head04_MockitoBasic.example01;

import com.example.springtdd.Head04_MockitoBasic.service.AuditService;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import com.example.springtdd.Head04_MockitoBasic.service.PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import com.example.springtdd.Head04_MockitoBasic.service.*;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplexUserServiceTest {

    @Mock private UserNewRepository userNewRepository;
    @Mock private EmailService emailService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuditService auditService;

    @InjectMocks private UserNewService userNewService;

    @Test
    @DisplayName("회원가입 실패 시 이메일이 전송되지 않아야 한다")
    void whenUserRegistrationFails_thenEmailShouldNotBeSent() {

        // Given
        UserNew user = UserNew.builder()
                .email("invalid@example.com")
                .name("홍길동")
                .status("ACTIVE")
                .build();

        when(userNewRepository.save(any(UserNew.class)))
                .thenThrow(new DataIntegrityViolationException("이메일 중복"));

        // When & Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            userNewService.register(user);
        });

        verify(emailService, never()).send(anyString(), anyString());
        verify(auditService).logFailedRegistration(user.getEmail());
    }

    @Test
    @DisplayName("비밀번호 암호화 후 사용자 저장")
    void whenRegisterUser_thenPasswordShouldBeEncoded() {

        // Given
        UserNew user = UserNew.builder()
                .email("test@example.com")
                .name("홍길동")
                .status("ACTIVE")
                .password("rawPassword")
                .build();

        String encodedPassword = "encodedPassword123";

        when(passwordEncoder.encode("rawPassword")).thenReturn(encodedPassword);

        when(userNewRepository.save(any(UserNew.class))).thenAnswer(invocation -> {
            UserNew savedUser = invocation.getArgument(0);

            // 🔥 핵심 검증
            assertEquals(encodedPassword, savedUser.getPassword());

            return savedUser;
        });

        when(emailService.send(anyString(), anyString())).thenReturn(true);

        // When
        userNewService.register(user);

        // Then
        verify(passwordEncoder).encode("rawPassword");
        verify(userNewRepository).save(any(UserNew.class));
        verify(emailService).send(anyString(), anyString());
    }
}