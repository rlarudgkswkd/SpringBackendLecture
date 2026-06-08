package com.codeit.springnginxhttpsdemo.controller;

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
                "requestSecure", request.isSecure(),
                "requestScheme", request.getScheme(),
                "serverPort", request.getServerPort(),
                "host", getHeader(request, "Host"),
                "xForwardedProto", getHeader(request, "X-Forwarded-Proto"),
                "xForwardedFor", getHeader(request, "X-Forwarded-For"),
                "xRealIp", getHeader(request, "X-Real-IP")
        );
    }

    private String getHeader(
            HttpServletRequest request,
            String name
    ) {

        String value = request.getHeader(name);

        if (value == null || value.isBlank()) {
            return "없음";
        }

        return value;
    }
}