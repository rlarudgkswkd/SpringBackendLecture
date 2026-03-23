package com.example.springtdd.Head06_ControllerTest.dto;


import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeliveryAddress {

    @Size(min = 5)
    private String zipCode;

    @NotBlank
    private String street;
}
