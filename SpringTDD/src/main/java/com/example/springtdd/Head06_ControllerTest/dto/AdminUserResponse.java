package com.example.springtdd.Head06_ControllerTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminUserResponse {

    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private boolean active;
}
