package com.example.springtdd.Head03_SpringDataJpaTest.example05;

import com.example.springtdd.Head03_SpringDataJpaTest.entity.User;
import com.example.springtdd.Head03_SpringDataJpaTest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableJpaRepositories(basePackages = "com.example.springtdd.Head03_SpringDataJpaTest.repository")
@EntityScan(basePackages = "com.example.springtdd.Head03_SpringDataJpaTest.entity")
class UserRepositorySetupTest {

    @Autowired
    private UserRepository userRepository;

    // 테스트에 사용할 샘플 데이터 준비
    @BeforeEach
    void setUp() {
        userRepository.save(new User(1L,"a@example.com","Alice", "ONLINE"));
        userRepository.save(new User(2L,"bob@example.com","bob", "ONLINE"));
    }

    @Test
    @DisplayName("기본 유저가 2명 존재해야 한다")
    void givenSetup_whenCountUsers_thenReturns2() {

        // When
        long count = userRepository.count();

        // Then
        assertEquals(2, count);
    }
}