package com.codeit.springasyncexceptiondemo.controller;

import com.codeit.springasyncexceptiondemo.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AsyncController {

    private final AsyncService asyncService;

    @GetMapping("/api/async/error")
    public Map<String, Object> asyncError() {

        System.out.println(
                now() + " [AsyncController] 요청 수신"
        );

        asyncService.asyncException(
                "test@test.com"
        );

        System.out.println(
                now() + " [AsyncController] 응답 반환"
        );

        return Map.of(
                "success", true
        );
    }

    private String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}