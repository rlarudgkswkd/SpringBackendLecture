package com.codeit.springsecurityjwtdemo.dto;

import lombok.Builder;

@Builder
public record LoginSuccessResponse(
        boolean success,
        String accessToken,
        String tokenType,
        String username
) {
}
