package com.example.springtdd.Head07_IntegrationTest.external;

import org.springframework.stereotype.Service;

@Service
public interface ExternalApiClient {

    boolean validateEmail(String email);
}
