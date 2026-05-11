package com.codeit.springsecuritysessionmanagementcore.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AuthenticationInfoResponse {

    private String username;

    private String authenticationClass;

    private String principalClass;

    private boolean authenticated;

    private List<String> authorities;

    private String sessionId;

    private String threadName;
}
