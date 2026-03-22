package com.example.head16_springstablehigh.exception;

import com.example.head16_springstablehigh.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmail(DuplicateEmailException e) {

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.of(
                        "USER_001",
                        e.getMessage(),
                        Map.of("email", e.getEmail())
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        return ResponseEntity
                .internalServerError()
                .body(ErrorResponse.of(
                        "SERVER_001",
                        "일시적인 서버 오류가 발생했습니다"
                ));
    }
}
