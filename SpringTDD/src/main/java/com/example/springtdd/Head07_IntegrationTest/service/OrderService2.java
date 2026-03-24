package com.example.springtdd.Head07_IntegrationTest.service;

import com.example.springtdd.Head07_IntegrationTest.domain.order.Order2;
import com.example.springtdd.Head07_IntegrationTest.domain.product.Product;
import com.example.springtdd.Head07_IntegrationTest.domain.user.User2;
import com.example.springtdd.Head07_IntegrationTest.dto.*;
import com.example.springtdd.Head07_IntegrationTest.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService2 {

    private final UserRepository2 userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        User2 user = userRepository.findById(request.getUserId()).orElseThrow();

        int total = 0;

        for (OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findByIdWithLock(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("상품 없음"));

            product.decreaseStock(item.getQuantity());
            total += item.getPrice() * item.getQuantity();
        }

        Order2 order = orderRepository.save(new Order2(user, total));

        // GOLD: 3%
        if (user.getGrade().name().equals("GOLD")) {
            user.addPoints(total * 3 / 100);
        }

        return new OrderResponse(order.getId());
    }
}
