package com.example.springtdd.Head05_SpringServiceLayerTest.example01;

import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Member;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Order;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Product;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.DiscountPolicy;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.ProductRepository;
import com.example.springtdd.Head05_SpringServiceLayerTest.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private ProductRepository productRepository;
    @Mock private DiscountPolicy discountPolicy;

    @InjectMocks private OrderService orderService;

    @Test
    void vipMemberDiscountLogic() {

        // Given
        Member vip = new Member("VIP");
        Product product = new Product(150000);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(discountPolicy.calculateDiscount(vip, 150000)).thenReturn(30000);

        // When
        Order order = orderService.createOrder(vip, 1L, 1);

        // Then
        assertEquals(120000, order.getFinalAmount());
        assertEquals(30000, order.getDiscountAmount());
    }
}