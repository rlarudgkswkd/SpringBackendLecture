package com.example.springtdd.Head03_SpringDataJpaTest.example01;

import com.example.springtdd.Head03_SpringDataJpaTest.entity.User;
import com.example.springtdd.Head03_SpringDataJpaTest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/menudb",
        "spring.datasource.username=menu_user",
        "spring.datasource.password=menu_pass",
        "spring.datasource.driver-class-name=org.postgresql.Driver", // ⭐ 추가
        "spring.jpa.hibernate.ddl-auto=none"
})
class UserRepositoryIntegrityTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이메일이 NULL이면 저장 실패해야 한다")
    void nullEmail_shouldFail() {

        // Given
        User user = User.builder()
                .email(null)
                .name("Test")
                .status("ACTIVE")
                .build();

        // When & Then
        DataIntegrityViolationException exception =
                assertThrows(DataIntegrityViolationException.class, () -> {
                    userRepository.saveAndFlush(user);
                });

        // Then (예외 메시지 출력)
        System.out.println("=== NOT NULL 예외 메시지 ===");
        System.out.println(exception.getMessage());
    }

    @Test
    @DisplayName("이메일이 중복되면 저장 실패해야 한다")
    void duplicateEmail_shouldFail() {

        // Given
        userRepository.saveAndFlush(
                User.builder()
                        .email("dup@test.com")
                        .name("A")
                        .status("ACTIVE")
                        .build()
        );

        User duplicate = User.builder()
                .email("dup@test.com")
                .name("B")
                .status("ACTIVE")
                .build();

        // When & Then
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.saveAndFlush(duplicate);
        });
    }
}