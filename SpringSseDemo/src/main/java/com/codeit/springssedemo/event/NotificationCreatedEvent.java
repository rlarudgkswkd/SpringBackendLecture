package com.codeit.springssedemo.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationCreatedEvent {

    private String targetUserId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}