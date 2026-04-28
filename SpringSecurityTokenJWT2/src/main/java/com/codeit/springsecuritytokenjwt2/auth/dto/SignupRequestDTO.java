package com.codeit.springsecuritytokenjwt2.auth.dto;

/**
 * 회원가입 요청 DTO
 */
public class SignupRequestDTO {

    private String username;        // 사용자 로그인 ID
    private String email;           // 이메일 주소
    private String password;        // 사용자 로그인 PW

    public SignupRequestDTO() {}

    public SignupRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupRequestDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}