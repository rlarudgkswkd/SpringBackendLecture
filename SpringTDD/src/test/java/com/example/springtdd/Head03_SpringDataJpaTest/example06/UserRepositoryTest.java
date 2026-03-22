package com.example.springtdd.Head03_SpringDataJpaTest.example06;

import com.example.springtdd.Head03_SpringDataJpaTest.entity.User;
import com.example.springtdd.Head03_SpringDataJpaTest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // TestFixture - 기본 사용자 생성
    private User createUser(String email, String name) {
        return User.builder()
                .email(email)
                .name(name)
                .status("ACTIVE")
                .build();
    }

    // TestFixture - 상태를 포함한 사용자 생성
    private User createUserWithStatus(String email, String name, String status) {
        return User.builder()
                .email(email)
                .name(name)
                .status(status)
                .build();
    }

    // TestFixture - 비활성 사용자
    private User createInactiveUser() {
        return User.builder()
                .email("inactive@test.com")
                .name("비활성유저")
                .status("INACTIVE")
                .build();
    }

    @Test
    @DisplayName("유저 생성 헬퍼를 통해 명확하게 테스트 작성 가능")
    void givenUserFactory_whenSave_thenUserIsPersisted() {

        // Given
        User user = createUser("factory@example.com", "Factory");

        // When
        userRepository.save(user);

        // Then
        Optional<User> found = userRepository.findById(user.getId());

        assertTrue(found.isPresent());
        assertEquals("factory@example.com", found.get().getEmail());
    }

    @Test
    @DisplayName("이메일로 사용자 조회")
    void findByEmail() {

        // Given
        User user = createUser("specific@email.com", "특정유저");
        userRepository.save(user);

        // When
        Optional<User> found = userRepository.findByEmail("specific@email.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("특정유저");
    }

    @Test
    @DisplayName("상태 기반 사용자 구분")
    void handleInactiveUser() {

        // Given
        User inactiveUser = createInactiveUser();

        // When
        userRepository.save(inactiveUser);

        // Then
        List<User> result = userRepository.findByStatus("INACTIVE");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getEmail()).isEqualTo("inactive@test.com");
    }
}