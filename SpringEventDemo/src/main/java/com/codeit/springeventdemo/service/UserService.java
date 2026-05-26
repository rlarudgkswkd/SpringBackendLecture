package com.codeit.springeventdemo.service;

import com.codeit.springeventdemo.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void registerUser(String email) {

        String userId =
                UUID.randomUUID().toString();

        System.out.println(
                now() + " [UserService] 회원가입 시작"
        );

        System.out.println(
                now() + " [UserService] 실행 스레드: "
                        + Thread.currentThread().getName()
        );

        eventPublisher.publishEvent(
                new UserRegisteredEvent(userId, email)
        );

        System.out.println(
                now() + " [UserService] 회원가입 완료"
        );
    }

    private String now() {
        return "[" + LocalTime.now().withNano(0) + "]";
    }
}