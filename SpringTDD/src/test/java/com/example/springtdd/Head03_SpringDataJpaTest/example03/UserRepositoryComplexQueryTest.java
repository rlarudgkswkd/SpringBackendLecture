package com.example.springtdd.Head03_SpringDataJpaTest.example03;

import com.example.springtdd.Head03_SpringDataJpaTest.entity.User;
import com.example.springtdd.Head03_SpringDataJpaTest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/menudb",
        "spring.datasource.username=menu_user",
        "spring.datasource.password=menu_pass",
        "spring.jpa.hibernate.ddl-auto=none"
})
class UserRepositoryComplexQueryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("상태 + 이름 조건 조회")
    void complexQuery() {

        // Given
        userRepository.save(User.builder()
                .email("a@test.com")
                .name("Alice")
                .status("ACTIVE")
                .build());

        userRepository.save(User.builder()
                .email("b@test.com")
                .name("Alice")
                .status("INACTIVE")
                .build());

        // When
        List<User> result = userRepository.findByStatusAndName("ACTIVE", "Alice");

        // Then
        assertEquals(1, result.size());
        assertEquals("a@test.com", result.get(0).getEmail());
    }
}