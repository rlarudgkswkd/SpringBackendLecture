package com.example.springtdd.Head04_MockitoBasic.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmailService {
    boolean send(String email, String message);

    String getResponse();

    int getCount();

    String getLastMessage();

    void sendBatch(List<String> messages);
}