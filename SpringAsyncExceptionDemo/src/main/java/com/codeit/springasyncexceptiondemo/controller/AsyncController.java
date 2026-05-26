package com.codeit.springasyncexceptiondemo.controller;

import com.codeit.springasyncexceptiondemo.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AsyncController {

    private final AsyncService asyncService;

    @GetMapping("/api/async/error")
    public Map<String, Object> asyncError() {

        asyncService.asyncException(
                "test@test.com"
        );

        return Map.of(
                "success", true
        );
    }

    @GetMapping("/api/async/completable")
    public Map<String, Object> completable() {

        asyncService.asyncCompletableException();

        return Map.of(
                "success", true
        );
    }

    @GetMapping("/api/async/retry")
    public Map<String, Object> retry() {

        asyncService.callExternalApi(
                "retry@test.com"
        );

        return Map.of(
                "success", true
        );
    }

    @GetMapping("/api/async/mdc")
    public Map<String, Object> mdc() {

        String requestId =
                UUID.randomUUID().toString();

        MDC.put(
                "requestId",
                requestId
        );

        System.out.println(
                now() + " [AsyncController] requestId: "
                        + MDC.get("requestId")
        );

        asyncService.asyncMdcTask();

        MDC.clear();

        return Map.of(
                "requestId",
                requestId
        );
    }

    private String now() {

        return "[" + LocalTime.now().withNano(0) + "]";
    }
}