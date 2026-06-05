package com.codeit.springssedemo.service;

import com.codeit.springssedemo.dto.CreateNotificationRequest;
import com.codeit.springssedemo.dto.NotificationResponse;
import com.codeit.springssedemo.event.NotificationCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class NotificationService {

    private final SseConnectionManager connectionManager;
    private final ApplicationEventPublisher eventPublisher;

    public NotificationService(
            SseConnectionManager connectionManager,
            ApplicationEventPublisher eventPublisher
    ) {
        this.connectionManager = connectionManager;
        this.eventPublisher = eventPublisher;
    }

    public SseEmitter createNotificationStream(String userId) {

        return connectionManager.createConnection(userId);
    }

    public void createAndSendNotification(
            CreateNotificationRequest request
    ) {

        NotificationCreatedEvent event =
                new NotificationCreatedEvent(
                        request.getTargetUserId(),
                        request.getTitle(),
                        request.getContent(),
                        LocalDateTime.now()
                );

        printLog(
                "알림 이벤트 발행: targetUserId="
                        + request.getTargetUserId()
        );

        eventPublisher.publishEvent(event);
    }

    @Async
    @EventListener
    public void handleNotificationCreated(
            NotificationCreatedEvent event
    ) {

        printLog(
                "알림 이벤트 수신: targetUserId="
                        + event.getTargetUserId()
        );

        NotificationResponse response =
                new NotificationResponse(
                        event.getTitle(),
                        event.getContent(),
                        LocalTime.now().withNano(0).toString()
                );

        connectionManager.sendToUser(
                event.getTargetUserId(),
                response
        );
    }

    private void printLog(String message) {

        System.out.println(
                "[" + LocalTime.now().withNano(0)
                        + "] [NotificationService] "
                        + message
        );
    }
}