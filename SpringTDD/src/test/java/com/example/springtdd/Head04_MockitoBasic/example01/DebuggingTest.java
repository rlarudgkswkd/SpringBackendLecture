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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DebuggingTest {

    @Mock private UserNewRepository userRepository;
    @InjectMocks private UserNewService userService;

    @Test
    @DisplayName("주입이 제대로 되었는지 확인하는 방법")
    void checkInjectionStatus() {

        // 주입된 Mock 객체가 null이 아닌지 확인
        assertNotNull(userService);

        // 실제로 Mock 객체가 동작하는지 테스트
        when(userRepository.findById(1L)).thenReturn(Optional.of(new UserNew()));

        // 메서드 호출 후 검증
        Optional<UserNew> userNew = userRepository.findById(1L);
        assertTrue(userNew.isPresent());

        // Mock 객체 호출 검증
        verify(userRepository).findById(1L);
    }
}