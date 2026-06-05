package com.codeit.springssedemo.controller;

import com.codeit.springssedemo.dto.CreateNotificationRequest;
import com.codeit.springssedemo.service.NotificationService;
import com.codeit.springssedemo.service.SseConnectionManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final SseConnectionManager connectionManager;

    public NotificationController(
            NotificationService notificationService,
            SseConnectionManager connectionManager
    ) {
        this.notificationService = notificationService;
        this.connectionManager = connectionManager;
    }

    @GetMapping(
            value = "/notifications/stream/{userId}",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter streamNotifications(
            @PathVariable String userId
    ) {

        return notificationService
                .createNotificationStream(userId);
    }

    @PostMapping("/notifications/send")
    public Map<String, Object> sendNotification(
            @RequestBody CreateNotificationRequest request
    ) {

        notificationService.createAndSendNotification(request);

        return Map.of(
                "success", true,
                "targetUserId", request.getTargetUserId()
        );
    }

    @GetMapping("/notifications/connections/{userId}")
    public Map<String, Object> getConnectionCount(
            @PathVariable String userId
    ) {

        return Map.of(
                "userId", userId,
                "connectionCount",
                connectionManager.getConnectionCount(userId)
        );
    }
}