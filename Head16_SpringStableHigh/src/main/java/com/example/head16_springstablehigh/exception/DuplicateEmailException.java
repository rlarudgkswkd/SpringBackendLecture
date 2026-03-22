package com.example.head16_springstablehigh.exception;

public class DuplicateEmailException extends RuntimeException {

    private final String email;

    public DuplicateEmailException(String email) {
        super("이미 사용 중인 이메일입니다: " + email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
