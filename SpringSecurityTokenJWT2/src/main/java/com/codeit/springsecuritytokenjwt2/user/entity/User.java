package com.codeit.springsecuritytokenjwt2.user.entity;

import jakarta.persistence.*;

/**
 * 사용자 엔티티
 */
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;                        // 사용자 고유 ID

    @Column(length = 50, nullable = false, unique = true)
    private String username;                    // 사용자명 (로그인 ID)

    @Column(length = 100, nullable = false, unique = true)
    private String email;                       // 이메일 주소

    @Column(length = 60, nullable = false)
    private String password;                    // BCrypt 암호화된 비밀번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;             // 사용자 권한 (ADMIN, USER)

    protected User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

        // 사용자의 기본 권한은 관리자가 아니라 일반 사용자로 설정
        this.role = Role.USER;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}