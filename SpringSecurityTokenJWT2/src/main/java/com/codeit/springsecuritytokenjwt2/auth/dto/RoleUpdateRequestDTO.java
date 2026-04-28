package com.codeit.springsecuritytokenjwt2.auth.dto;

import com.codeit.springsecuritytokenjwt2.user.entity.Role;

/**
 * 사용자 권한 수정 요청 DTO
 */
public class RoleUpdateRequestDTO {

    private Long userId;           // 권한을 수정할 사용자의 ID
    private Role newRole;          // 새로 할당할 권한

    public RoleUpdateRequestDTO() {}

    public RoleUpdateRequestDTO(Long userId, Role newRole) {
        this.userId = userId;
        this.newRole = newRole;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Role getNewRole() {
        return newRole;
    }

    public void setNewRole(Role newRole) {
        this.newRole = newRole;
    }

    @Override
    public String toString() {
        return "RoleUpdateRequestDTO{" +
                "userId=" + userId +
                ", newRole=" + newRole +
                '}';
    }
}