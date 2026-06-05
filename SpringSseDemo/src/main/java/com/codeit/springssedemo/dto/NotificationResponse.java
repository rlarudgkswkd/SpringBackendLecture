package com.codeit.springssedemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationResponse {

    private String title;
    private String content;
    private String sentAt;
}