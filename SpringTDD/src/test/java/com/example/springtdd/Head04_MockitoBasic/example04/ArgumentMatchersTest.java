package com.example.springtdd.Head04_MockitoBasic.example04;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import com.example.springtdd.Head04_MockitoBasic.service.BatchService;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArgumentMatchersTest {

    // ✅ 필드에서만 @Mock 사용
    @org.mockito.Mock private UserNewRepository userRepository;
    @org.mockito.Mock private EmailService emailService;

    @Test
    @DisplayName("기본적인 ArgumentMatchers 사용법")
    void basicArgumentMatchers() {

        when(userRepository.save(any(UserNew.class)))
                .thenReturn(UserNew.builder().email("saved@example.com").name("저장됨").status("ACTIVE").build());

        when(emailService.send(anyString(), anyString())).thenReturn(true);

        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.of(UserNew.builder().email("a@test.com").name("테스터").status("ACTIVE").build()));

        UserNew user1 = UserNew.builder().email("test1@example.com").name("사용자1").status("ACTIVE").build();
        UserNew user2 = UserNew.builder().email("test2@example.com").name("사용자2").status("ACTIVE").build();

        userRepository.save(user1);
        userRepository.save(user2);

        emailService.send("test@example.com", "안녕하세요");
        emailService.send("admin@example.com", "관리자님");

        userRepository.findById(1L);

        verify(userRepository, times(2)).save(any(UserNew.class));
        verify(emailService, times(2)).send(anyString(), anyString());
        verify(userRepository).findById(anyLong());
    }

    @Test
    @DisplayName("문자열 패턴 매칭 ArgumentMatchers")
    void stringPatternMatching() {

        EmailService emailService = mock(EmailService.class);

        when(emailService.send(contains("@gmail.com"), anyString())).thenReturn(true);
        when(emailService.send(startsWith("admin"), anyString())).thenReturn(false);
        when(emailService.send(endsWith("@company.com"), anyString())).thenReturn(true);
        when(emailService.send(matches(".*@test\\.com"), anyString())).thenReturn(true);

        assertTrue(emailService.send("user@gmail.com", "Hello"));
        assertFalse(emailService.send("admin@example.com", "Hello"));
        assertTrue(emailService.send("employee@company.com", "Hello"));
        assertTrue(emailService.send("tester@test.com", "Hello"));

        verify(emailService).send(contains("@gmail.com"), eq("Hello"));
        verify(emailService).send(startsWith("admin"), eq("Hello"));
    }

    @Test
    @DisplayName("조건부 매칭과 커스텀 ArgumentMatchers")
    void conditionalMatching() {

        UserNewRepository userRepository = mock(UserNewRepository.class);

        when(userRepository.save(argThat(user ->
                user.getEmail() != null &&
                        user.getName() != null &&
                        user.getEmail().contains("@valid.com") &&
                        user.getName().length() > 2
        ))).thenReturn(
                UserNew.builder()
                        .email("saved@valid.com")
                        .name("저장된사용자")
                        .status("ACTIVE")
                        .build()
        );

        UserNew validUser = UserNew.builder()
                .email("test@valid.com")
                .name("홍길동")
                .status("ACTIVE")
                .build();

        UserNew invalidUser1 = UserNew.builder()
                .email("test@invalid.com")
                .name("홍길동")
                .status("ACTIVE")
                .build();

        UserNew invalidUser2 = UserNew.builder()
                .email("test@valid.com")
                .name("김")
                .status("ACTIVE")
                .build();

        // When & Then
        assertNotNull(userRepository.save(validUser));
        assertNull(userRepository.save(invalidUser1));
        assertNull(userRepository.save(invalidUser2));

        verify(userRepository).save(argThat(user ->
                user.getEmail() != null &&
                        user.getName() != null &&
                        user.getEmail().contains("@valid.com") &&
                        user.getName().length() > 2
        ));
    }

    @Test
    @DisplayName("컬렉션 ArgumentMatchers")
    void collectionAndArrayMatching() {

        BatchService batchService = mock(BatchService.class);

        when(batchService.processUsers(anyList())).thenReturn(true);

        when(batchService.processEmails(argThat(emails ->
                emails.size() > 0 && emails.stream().allMatch(e -> e.contains("@"))
        ))).thenReturn("완료");

        List<UserNew> userList = Arrays.asList(
                UserNew.builder().email("u1@test.com").name("A").status("ACTIVE").build(),
                UserNew.builder().email("u2@test.com").name("B").status("ACTIVE").build()
        );

        List<String> validEmails = Arrays.asList("a@test.com", "b@test.com");
        List<String> invalidEmails = Arrays.asList("invalid", "b@test.com");

        assertTrue(batchService.processUsers(userList));
        assertEquals("완료", batchService.processEmails(validEmails));
        assertNull(batchService.processEmails(invalidEmails));

        verify(batchService).processUsers(anyList());
        verify(batchService, times(2))
                .processEmails(argThat(list -> list.size() == 2));
    }

    @Test
    @DisplayName("ArgumentMatchers 혼합 사용")
    void mixedArgumentMatchers() {

        EmailService emailService = mock(EmailService.class);

        when(emailService.send(anyString(), eq("Hello"))).thenReturn(true);
        when(emailService.send("test@example.com", "Hello")).thenReturn(false);

        assertTrue(emailService.send("any@example.com", "Hello"));
        assertFalse(emailService.send("test@example.com", "Hello"));

        verify(emailService, times(2)).send(anyString(), eq("Hello"));
        verify(emailService).send(eq("test@example.com"), eq("Hello"));
    }
}
