package com.example.springtdd.Head04_MockitoBasic.service;

import org.springframework.stereotype.Service;

@Service
public interface AuditService {

    void logUserRegistration(String email);

    void logFailedRegistration(String email);
}