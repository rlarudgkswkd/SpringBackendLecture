package com.example.springtdd.Head04_MockitoBasic.example01;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import com.example.springtdd.Head04_MockitoBasic.service.AuditService;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import com.example.springtdd.Head04_MockitoBasic.service.UserNewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserNewServiceTest {

    @Mock
    private UserNewRepository userNewRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private UserNewService userNewService;

    @Test
    @DisplayName("회원가입 성공 시 저장 후 이메일이 전송되어야 한다")
    void register_success() {
        // Given
        UserNew userNew = UserNew.builder()
                .id(1L)
                .email("test@example.com")
                .name("홍길동")
                .status("ONLINE")
                .build();

        when(userNewRepository.save(any(UserNew.class))).thenReturn(userNew);
        when(emailService.send(anyString(), anyString())).thenReturn(true);

        // When
        boolean result = userNewService.register(userNew);

        // Then
        assertTrue(result);
        verify(userNewRepository).save(any(UserNew.class));
        verify(emailService).send(eq("test@example.com"), contains("가입"));
    }

    @Test
    @DisplayName("회원가입 실패 시 이메일은 전송되지 않고 감사 로그가 기록되어야 한다")
    void register_fail_shouldCallAudit() {
        // Given
        UserNew userNew = UserNew.builder()
                .id(1L)
                .email("fail@example.com")
                .name("홍길동")
                .status("ONLINE")
                .build();

        when(userNewRepository.save(any(UserNew.class)))
                .thenThrow(new DataIntegrityViolationException("이메일 중복"));

        // When & Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            userNewService.register(userNew);
        });

        verify(emailService, never()).send(anyString(), anyString());
        verify(auditService).logFailedRegistration("fail@example.com");
    }
}