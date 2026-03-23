package com.example.springtdd.Head06_ControllerTest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @Email(message = "이메일 형식 오류")
    @NotBlank(message = "이메일은 필수입니다")
    private String email;

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
