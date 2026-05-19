package com.codeit.springsecurityjwtdemo.dto;

import lombok.Builder;

@Builder
public record TokenRefreshResponse(
        boolean success,
        String accessToken,
        String tokenType
) {
}
