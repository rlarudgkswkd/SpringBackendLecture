package com.codeit.springhttpsdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public Map<String, Object> health(
            HttpServletRequest request
    ) {

        return Map.of(
                "status", "UP",
                "secure", request.isSecure(),
                "scheme", request.getScheme(),
                "serverPort", request.getServerPort()
        );
    }
}