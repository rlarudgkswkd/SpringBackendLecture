package com.codeit.springsecuritysessionbasedarchi.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class LoginSuccessInfo {

    private String username;

    private String sessionId;

    private LocalDateTime loginTime;

    private String userAgent;

    private String ipAddress;

    private List<String> authorities;
}
