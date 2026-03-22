package com.example.springtdd.Head04_MockitoBasic.service;

import org.springframework.stereotype.Service;

@Service
public interface PasswordEncoder {
    String encode(String rawPassword);
}
