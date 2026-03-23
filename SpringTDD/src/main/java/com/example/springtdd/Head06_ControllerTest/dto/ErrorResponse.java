package com.example.springtdd.Head06_ControllerTest.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private String message;
    private String errorCode;
    private String path;
    private boolean retryable; // 🔥 추가
    private LocalDateTime timestamp;

    public ErrorResponse(String errorCode, String message, String path) {
        this(errorCode, message, path, false);
    }

    public ErrorResponse(String errorCode, String message, String path, boolean retryable) {
        this.message = message;
        this.errorCode = errorCode;
        this.path = path;
        this.retryable = retryable;
        this.timestamp = LocalDateTime.now();
    }
}