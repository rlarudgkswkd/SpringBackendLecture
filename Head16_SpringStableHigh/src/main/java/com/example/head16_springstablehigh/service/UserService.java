package com.example.head16_springstablehigh.service;

import com.example.head16_springstablehigh.exception.DuplicateEmailException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final Set<String> emails = new HashSet<>();

    public void register(String email) {

        // 비즈니스 규칙: 이메일 중복 금지
        if (emails.contains(email)) {
            throw new DuplicateEmailException(email);
        }

        emails.add(email);
    }
}