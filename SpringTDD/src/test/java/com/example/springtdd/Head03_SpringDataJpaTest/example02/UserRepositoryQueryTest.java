package com.example.springtdd.Head03_SpringDataJpaTest.example02;

import com.example.springtdd.Head03_SpringDataJpaTest.entity.User;
import com.example.springtdd.Head03_SpringDataJpaTest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/menudb",
        "spring.datasource.username=menu_user",
        "spring.datasource.password=menu_pass",
        "spring.datasource.driver-class-name=org.postgresql.Driver", // ⭐ 추가
        "spring.jpa.hibernate.ddl-auto=none"
})
@EnableJpaRepositories(basePackages = "com.example.springtdd.Head03_SpringDataJpaTest.repository")
@EntityScan(basePackages = "com.example.springtdd.Head03_SpringDataJpaTest.entity")
class UserRepositoryQueryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("@Query로 이메일 조회")
    void queryTest() {

        // Given
        userRepository.save(
                User.builder()
                        .email("query@test.com")
                        .name("Query")
                        .status("ACTIVE")
                        .build()
        );

        // When
        Optional<User> result = userRepository.findByEmailWithQuery("query@test.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals("Query", result.get().getName());
    }
}