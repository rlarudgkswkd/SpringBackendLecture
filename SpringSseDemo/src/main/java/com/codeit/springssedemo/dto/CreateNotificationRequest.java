package com.codeit.springssedemo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateNotificationRequest {

    private String targetUserId;
    private String title;
    private String content;
}