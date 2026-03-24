package com.example.springtdd.Head07_IntegrationTest.example02;

import com.example.springtdd.Head07_IntegrationTest.dto.RegisterRequest;
import com.example.springtdd.Head07_IntegrationTest.dto.UserResponse;
import com.example.springtdd.Head07_IntegrationTest.external.ExternalApiClient;
import com.example.springtdd.Head07_IntegrationTest.repository.UserRepository2;
import com.example.springtdd.Head07_IntegrationTest.service.EmailService;
import com.example.springtdd.Head07_IntegrationTest.service.UserService2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserServiceIntegrationTest.TestConfig.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.external-api.enabled=false"
})
class UserServiceIntegrationTest {

    @Autowired private UserService2 userService;
    @Autowired private EmailService emailService;

    @MockBean private ExternalApiClient externalApiClient;

    @Test
    @DisplayName("외부 API 비활성화 상태에서 회원가입이 정상 동작해야 한다")
    void userRegistrationWithTestProfile() {
        RegisterRequest request = new RegisterRequest(
                "test@example.com",
                "password123",
                "홍길동"
        );

        when(externalApiClient.validateEmail(anyString())).thenReturn(true);

        UserResponse response = userService.register(request);

        assertNotNull(response.getId());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("홍길동", response.getName());

        verify(externalApiClient, never()).validateEmail(anyString());
    }

    @Test
    @DisplayName("H2 기반 빠른 대량 처리 테스트")
    void fastDatabaseOperations() {
        List<RegisterRequest> requests = IntStream.range(0, 100)
                .mapToObj(i -> new RegisterRequest(
                        "user" + i + "@example.com",
                        "password",
                        "사용자" + i
                ))
                .collect(Collectors.toList());

        long start = System.currentTimeMillis();

        List<UserResponse> responses = requests.stream()
                .map(userService::register)
                .collect(Collectors.toList());

        long end = System.currentTimeMillis();

        assertEquals(100, responses.size());
        assertTrue(end - start < 5000);

        responses.forEach(r -> {
            assertNotNull(r.getId());
            assertTrue(r.getEmail().contains("@example.com"));
        });
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(basePackages = "com.example.springtdd.Head07_IntegrationTest.repository")
    @EntityScan(basePackages = "com.example.springtdd.Head07_IntegrationTest.domain")
    static class TestConfig {

        @Bean
        UserService2 userService(
                UserRepository2 userRepository,
                EmailService emailService,
                ExternalApiClient externalApiClient
        ) {
            return new UserService2(userRepository, emailService, externalApiClient);
        }

        @Bean
        EmailService emailService() {
            return new EmailService();
        }
    }
}