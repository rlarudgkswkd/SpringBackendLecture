package com.example.springtdd.Head02_JUnitBasic.example03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void userValidation() {

        User user = new User("kim@example.com", "kim");

        assertAll("사용자 검증",
                () -> assertEquals("kim", user.getName()),
                () -> assertTrue(user.getEmail().contains("@")),
                () -> assertNotNull(user)
        );
        System.out.println("UserTest - userValidation 실행");
    }
}
