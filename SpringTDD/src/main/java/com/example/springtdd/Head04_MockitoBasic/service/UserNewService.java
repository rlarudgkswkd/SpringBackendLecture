package com.example.springtdd.Head04_MockitoBasic.service;

import com.example.springtdd.Head04_MockitoBasic.entity.UserNew;
import com.example.springtdd.Head04_MockitoBasic.repository.UserNewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNewService {

    private final UserNewRepository userNewRepository;
    private final EmailService emailService;
    private final AuditService auditService;
    private final PasswordEncoder passwordEncoder;

    public boolean register(UserNew userNew) {

        try {
            // 🔥 비밀번호 암호화 (테스트 핵심 포인트)
            if (userNew.getPassword() != null) {
                String encoded = passwordEncoder.encode(userNew.getPassword());
                userNew = userNew.toBuilder().password(encoded).build();
            }

            UserNew savedUser = userNewRepository.save(userNew);

            boolean emailSent = emailService.send(
                    userNew.getEmail(),
                    "가입을 환영합니다!"
            );

            return savedUser != null && emailSent;

        } catch (DataIntegrityViolationException e) {
            auditService.logFailedRegistration(userNew.getEmail());
            throw e;
        }
    }
}