package com.example.springtdd.Head07_IntegrationTest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeliveryAddress {
    private String zipCode;
    private String address;
    private String detail;
}
