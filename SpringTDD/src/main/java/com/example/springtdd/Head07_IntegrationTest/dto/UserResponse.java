package com.example.springtdd.Head07_IntegrationTest.dto;

public class UserResponse {

    private Long id;
    private String email;
    private String name;

    public UserResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public UserResponse(Long id) {
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
}
