package com.example.head16_springstablehigh.controller;

import com.example.head16_springstablehigh.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logging")
public class LoggingPracticeController {

    private final LoggingPracticeService loggingPracticeService;
    private final BadLoggingService badLoggingService;
    private final GoodLoggingService goodLoggingService;
    private final ExceptionLoggingService exceptionLoggingService;
    private final SecurityLoggingService securityLoggingService;

    public LoggingPracticeController(
            LoggingPracticeService loggingPracticeService,
            BadLoggingService badLoggingService,
            GoodLoggingService goodLoggingService,
            ExceptionLoggingService exceptionLoggingService,
            SecurityLoggingService securityLoggingService
    ) {
        this.loggingPracticeService = loggingPracticeService;
        this.badLoggingService = badLoggingService;
        this.goodLoggingService = goodLoggingService;
        this.exceptionLoggingService = exceptionLoggingService;
        this.securityLoggingService = securityLoggingService;
    }

    @GetMapping("/level")
    public String testLevels() {
        loggingPracticeService.runBasicLogs();
        return "로그 레벨 테스트 완료";
    }

    @GetMapping("/bad")
    public String bad() {
        badLoggingService.runBadExample();
        return "bad logging 실행";
    }

    @GetMapping("/good")
    public String good() {
        goodLoggingService.runGoodExample();
        return "good logging 실행";
    }

    @GetMapping("/exception")
    public String exception(@RequestParam String type) {
        exceptionLoggingService.runExceptionCase(type);
        return "exception logging 실행";
    }

    @GetMapping("/security")
    public String security(@RequestParam String email,
                           @RequestParam String password) {
        securityLoggingService.logUser(email, password);
        return "security logging 실행";
    }
}