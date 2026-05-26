package com.codeit.springeventdemo.event;

public record UserRegisteredEvent(
        String userId,
        String email
) {
}