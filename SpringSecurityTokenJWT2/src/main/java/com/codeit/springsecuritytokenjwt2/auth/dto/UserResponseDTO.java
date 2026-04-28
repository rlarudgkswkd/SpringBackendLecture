package com.codeit.springsecuritytokenjwt2.auth.dto;

/**
 * 사용자 응답 DTO
 */
public class UserResponseDTO {

    private Long id;                // 사용자 고유 ID
    private String username;        // 사용자명 (로그인 ID)
    private String email;           // 이메일 주소
    private String role;            // 사용자 권한 (ADMIN, USER)

    public UserResponseDTO() {}

    public UserResponseDTO(Long id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}