package com.example.springtdd.Head04_MockitoBasic.example02;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicStubbingTest {

    @Mock
    private UserNewRepository userRepository;

    @Test
    void basicWhenThenReturn() {

        UserNew expectedUser = UserNew.builder()
                .email("test@example.com")
                .name("홍길동")
                .status("ACTIVE")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(expectedUser));

        Optional<UserNew> actualUser = userRepository.findById(1L);

        assertTrue(actualUser.isPresent());
        assertEquals("test@example.com", actualUser.get().getEmail());
    }
}