package com.example.springtdd.Head04_MockitoBasic.example02;

import com.example.springtdd.Head03_SpringDataJpaTest.repository.UserRepository;
import com.example.springtdd.Head04_MockitoBasic.service.CountService;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VariousReturnTypesTest {
    @Test
    @DisplayName("다양한 타입의 반환값 설정")
    void variousReturnTypes() {

        EmailService emailService = mock(EmailService.class);
        CountService countService = mock(CountService.class);

        // Given - 각기 다른 타입의 반환값 설정
        // 1. boolean
        when(emailService.send(anyString(), anyString())).thenReturn(true);
        // 2. String
        when(emailService.getLastMessage()).thenReturn("Welcome!");
        // 3. int
        when(countService.getTotalCount()).thenReturn(42);
        // 4. List
        when(countService.getCountList()).thenReturn(Arrays.asList(1, 2, 3));

        // When & Then - 각각의 설정된 값이 반환되는지 확인
        // 1. boolean
        assertTrue(emailService.send("test@example.com", "Hello"));
        // 2. String
        assertEquals("Welcome!", emailService.getLastMessage());
        // 3. int
        assertEquals(42, countService.getTotalCount());
        // 4. List
        assertEquals(3, countService.getCountList().size());
    }

    @Test
    @DisplayName("연속 호출시 서로 다른 값 반환")
    void consecutiveCalls() {

        UserRepository userRepository = mock(UserRepository.class);

        // Given - 첫 번째, 두 번째, 세 번째 호출시 각각 다른 값 반환 설정
        when(userRepository.count())
                .thenReturn(10L)    // 첫 번째 호출
                .thenReturn(20L)    // 두 번째 호출
                .thenReturn(30L);   // 세 번째 호출

        // When & Then - 호출 순서에 따라 다른 값 반환 확인
        assertEquals(10L, userRepository.count());  // 첫 번째 호출
        assertEquals(20L, userRepository.count());  // 두 번째 호출
        assertEquals(30L, userRepository.count());  // 세 번째 호출
        assertEquals(30L, userRepository.count());  // 네 번째 이후는 마지막 값 계속 반환
    }
}
