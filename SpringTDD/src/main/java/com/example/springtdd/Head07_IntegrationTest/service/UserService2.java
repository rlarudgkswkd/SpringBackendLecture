package com.example.springtdd.Head07_IntegrationTest.service;

import com.example.springtdd.Head07_IntegrationTest.domain.user.User2;
import com.example.springtdd.Head07_IntegrationTest.dto.RegisterRequest;
import com.example.springtdd.Head07_IntegrationTest.dto.UserResponse;
import com.example.springtdd.Head07_IntegrationTest.external.ExternalApiClient;
import com.example.springtdd.Head07_IntegrationTest.repository.UserRepository2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService2 {

    private final UserRepository2 userRepository;
    private final EmailService emailService;
    private final ExternalApiClient externalApiClient;

    @Value("${app.external-api.enabled:true}")
    private boolean externalApiEnabled;

    public UserService2(UserRepository2 userRepository,
                       EmailService emailService,
                       ExternalApiClient externalApiClient) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.externalApiClient = externalApiClient;
    }

    public UserResponse register(RegisterRequest request) {

        // 🔥 profile 기반 분기
        if (externalApiEnabled) {
            externalApiClient.validateEmail(request.getEmail());
        }

        User2 user = new User2(
                request.getEmail(),
                request.getName(),
                null // UserGrade 필요하면 BASIC 등 넣어도 됨
        );

        User2 saved = userRepository.save(user);

        emailService.sendWelcomeEmail(saved.getEmail());

        return new UserResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getName()
        );
    }
}
