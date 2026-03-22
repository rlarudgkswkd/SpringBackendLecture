package com.example.springtdd.Head04_MockitoBasic.example03;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import com.example.springtdd.Head04_MockitoBasic.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicVerificationTest {

    // ✅ 필드에서만 @Mock 사용 가능
    @Mock
    private EmailService emailService;
    @Mock
    private UserNewRepository userRepository;
    @InjectMocks
    private UserNewService userNewService;

    @Test
    @DisplayName("기본적인 메서드 호출 검증")
    void basicMethodCallVerification() {

        UserNew user = UserNew.builder()
                .email("test@example.com")
                .name("홍길동")
                .status("ACTIVE")
                .build();

        when(userRepository.save(any(UserNew.class))).thenReturn(user);
        when(emailService.send(anyString(), anyString())).thenReturn(true);

        userNewService.register(user);

        verify(userRepository).save(user);
        verify(emailService).send("test@example.com", "가입을 환영합니다!");
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("메서드 호출 횟수 검증")
    void verifyCallCount() {

        NotificationService notificationService = mock(NotificationService.class);

        when(notificationService.send(anyString())).thenReturn(true);

        notificationService.send("첫 번째 알림");
        notificationService.send("두 번째 알림");
        notificationService.send("세 번째 알림");

        verify(notificationService, times(3)).send(anyString());
        verify(notificationService, times(1)).send("첫 번째 알림");
        verify(notificationService, atLeast(2)).send(anyString());
        verify(notificationService, atMost(5)).send(anyString());
        verify(notificationService, never()).sendBatch(anyList());
    }

    @Test
    @DisplayName("메서드 호출 순서 검증")
    void verifyCallOrder() {

        UserNewRepository userRepository = mock(UserNewRepository.class);
        EmailService emailService = mock(EmailService.class);
        AuditService auditService = mock(AuditService.class);

        UserNew user = UserNew.builder()
                .email("test@example.com")
                .name("홍길동")
                .status("ACTIVE")
                .build();

        when(userRepository.save(any(UserNew.class))).thenReturn(user);
        when(emailService.send(anyString(), anyString())).thenReturn(true);

        userRepository.save(user);
        emailService.send(user.getEmail(), "환영합니다");
        auditService.logUserRegistration(user.getEmail());

        InOrder inOrder = inOrder(userRepository, emailService, auditService);

        inOrder.verify(userRepository).save(user);
        inOrder.verify(emailService).send(user.getEmail(), "환영합니다");
        inOrder.verify(auditService).logUserRegistration(user.getEmail());
    }

    @Test
    @DisplayName("타임아웃을 고려한 비동기 메서드 호출 검증")
    void verifyWithTimeout() {

        AsyncEmailService asyncEmailService = mock(AsyncEmailService.class);

        CompletableFuture<Boolean> future = CompletableFuture.completedFuture(true);
        when(asyncEmailService.sendAsync(anyString(), anyString())).thenReturn(future);

        asyncEmailService.sendAsync("test@example.com", "Hello");

        verify(asyncEmailService, timeout(5000))
                .sendAsync("test@example.com", "Hello");

        verify(asyncEmailService, timeout(1000).times(1))
                .sendAsync(anyString(), anyString());
    }

    @Test
    @DisplayName("특정 메서드가 호출되지 않았음을 검증")
    void verifyNoInteractions() {

        EmailService emailService = mock(EmailService.class);
        SmsService smsService = mock(SmsService.class);

        // Given - 이메일 전송만 설정하고 SMS는 설정하지 않음
        when(emailService.send(anyString(), anyString())).thenReturn(true);

        // When - 이메일만 전송
        emailService.send("test@example.com", "Hello");

        // Then - SMS 서비스는 전혀 사용되지 않았음을 검증

        // smsService의 어떤 메서드도 호출되지 않았는지 확인
        Mockito.verifyNoInteractions(smsService);

        // emailService의 send는 호출되었지만 sendBatch는 호출되지 않았는지 확인
        verify(emailService).send(anyString(), anyString());
        verify(emailService, never()).sendBatch(anyList());
    }
}