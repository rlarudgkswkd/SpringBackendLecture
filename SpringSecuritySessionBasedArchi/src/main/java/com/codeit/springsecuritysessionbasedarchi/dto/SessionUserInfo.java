package com.codeit.springsecuritysessionbasedarchi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SessionUserInfo {

    private String username;

    private String sessionId;

    private String authenticationClass;
}
