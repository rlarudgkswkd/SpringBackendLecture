package com.example.springtdd.Head06_ControllerTest.example02;

import com.example.springtdd.Head06_ControllerTest.controller.OrderController;
import com.example.springtdd.Head06_ControllerTest.dto.CreateOrderRequest;
import com.example.springtdd.Head06_ControllerTest.exception.InsufficientInventoryException;
import com.example.springtdd.Head06_ControllerTest.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.hamcrest.Matchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private OrderService orderService;

    @Test
    @DisplayName("주문 생성 요청의 복합적인 검증 로직이 올바르게 동작해야 한다")
    @WithMockUser
    void orderCreationValidation() throws Exception {

        // Given - 유효하지 않은 요청 데이터
        String invalidOrderJson = """
            {
                "items": [
                    {
                        "productId": null,
                        "quantity": 0,
                        "price": -1000
                    },
                    {
                        "productId": 2,
                        "quantity": 1001,
                        "price": 50000
                    }
                ],
                "deliveryAddress": {
                    "zipCode": "123",
                    "street": ""
                },
                "paymentMethod": "INVALID_METHOD"
            }
            """;

        // When & Then - validation 실패 검증
        mockMvc.perform(post("/api/orders")
                        .with(csrf()) // POST 요청이므로 CSRF 필요
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidOrderJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(greaterThan(4))))
                .andExpect(jsonPath("$.errors[*].field", hasItems(
                        "items[0].productId",
                        "items[0].quantity",
                        "items[0].price",
                        "deliveryAddress.zipCode",
                        "deliveryAddress.street"
                )));

        // validation 실패 시 서비스 호출이 발생하지 않아야 한다
        verify(orderService, never()).createOrder(any());
    }

    @Test
    @DisplayName("재고 부족 예외에 대해 적절한 응답이 반환되어야 한다")
    @WithMockUser
    void inventoryShortageExceptionHandling() throws Exception {

        // Given - 정상 요청 데이터 구성
        String requestJson = """
        {
            "items": [
                {"productId": 1, "quantity": 10, "price": 5000},
                {"productId": 2, "quantity": 5, "price": 3000}
            ],
            "deliveryAddress": {
                "zipCode": "12345",
                "street": "서울시 강남구"
            },
            "paymentMethod": "CARD"
        }
        """;

        doThrow(new InsufficientInventoryException("상품 ID 1의 재고가 부족합니다. 현재 재고: 3개"))
                .when(orderService)
                .createOrder(Mockito.any(CreateOrderRequest.class));

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .with(csrf()) // POST 필수
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("상품 ID 1의 재고가 부족합니다. 현재 재고: 3개"))
                .andExpect(jsonPath("$.errorCode").value("INSUFFICIENT_INVENTORY"))
                .andExpect(jsonPath("$.retryable").value(false));
    }
}