package com.example.springtdd.Head06_ControllerTest.dto.user;

import jakarta.validation.constraints.*;

public class CreateUserRequest {

    @Email(message = "이메일 형식 오류")
    @NotBlank(message = "이메일 필수")
    private String email;

    @NotBlank(message = "이름 필수")
    private String name;

    @Min(value = 0, message = "나이는 0 이상")
    private int age;

    public CreateUserRequest() {}

    public CreateUserRequest(String email, String name, int age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public int getAge() { return age; }
}