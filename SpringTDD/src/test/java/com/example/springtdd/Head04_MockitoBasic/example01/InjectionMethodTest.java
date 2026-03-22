package com.example.springtdd.Head04_MockitoBasic.example01;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import com.example.springtdd.Head04_MockitoBasic.service.UserNewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InjectionMethodTest {

    @Mock
    private UserNewRepository userNewRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserNewService userNewService;

    @Test
    @DisplayName("생성자 주입 방식으로 Mock 객체들이 주입된다")
    void constructorInjectionTest() {

        // Given
        UserNew savedUser = UserNew.builder()
                .id(1L)
                .email("test@example.com")
                .name("홍길동")
                .status("ONLINE")
                .build();

        when(userNewRepository.save(any(UserNew.class)))
                .thenReturn(savedUser);

        when(emailService.send(anyString(), anyString()))
                .thenReturn(true);

        // When
        UserNew user = UserNew.builder()
                .email("test@example.com")
                .name("홍길동")
                .status("ONLINE")
                .build();

        boolean result = userNewService.register(user);

        // Then
        assertTrue(result);

        verify(userNewRepository).save(any(UserNew.class));
        verify(emailService).send(anyString(), anyString());
    }
}