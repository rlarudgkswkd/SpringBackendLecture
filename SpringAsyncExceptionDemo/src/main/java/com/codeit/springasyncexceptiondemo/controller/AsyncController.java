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

    @GetMapping("/api/async/void")
    public Map<String, Object> asyncVoid() {

        System.out.println(
                now() + " [AsyncController] void 요청"
        );

        try {

            asyncService.asyncVoidException();

        } catch (Exception e) {

            System.out.println(
                    now() + " [AsyncController] try-catch 예외 처리"
            );
        }

        return Map.of(
                "success", true,
                "message", "void 비동기 요청 완료"
        );
    }

    @GetMapping("/api/async/completable")
    public Map<String, Object> completable() {

        System.out.println(
                now() + " [AsyncController] completable 요청"
        );

        asyncService.asyncCompletableException();

        return Map.of(
                "success", true,
                "message", "CompletableFuture 요청 완료"
        );
    }

    private String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}