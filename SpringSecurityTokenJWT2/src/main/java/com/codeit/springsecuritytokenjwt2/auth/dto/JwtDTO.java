package com.codeit.springsecuritytokenjwt2.auth.dto;

/**
 * JWT 응답 DTO
 * 로그인/재발급 시 사용자 정보와 액세스 토큰을 함께 반환한다.
 */
public class JwtDTO {

    private UserResponseDTO user;
    private String accessToken;

    public JwtDTO() {
    }

    public JwtDTO(UserResponseDTO user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "JwtDTO{" +
                "user=" + user +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}


