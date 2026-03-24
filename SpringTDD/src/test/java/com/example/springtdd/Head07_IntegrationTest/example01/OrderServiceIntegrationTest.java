package com.example.springtdd.Head07_IntegrationTest.example01;

import com.example.springtdd.Head07_IntegrationTest.domain.order.Order2;
import com.example.springtdd.Head07_IntegrationTest.domain.order.OrderStatus;
import com.example.springtdd.Head07_IntegrationTest.domain.product.Product;
import com.example.springtdd.Head07_IntegrationTest.domain.user.User2;
import com.example.springtdd.Head07_IntegrationTest.domain.user.UserGrade;
import com.example.springtdd.Head07_IntegrationTest.dto.CreateOrderRequest;
import com.example.springtdd.Head07_IntegrationTest.dto.DeliveryAddress;
import com.example.springtdd.Head07_IntegrationTest.dto.OrderItemRequest;
import com.example.springtdd.Head07_IntegrationTest.dto.OrderResponse;
import com.example.springtdd.Head07_IntegrationTest.dto.PaymentMethod;
import com.example.springtdd.Head07_IntegrationTest.external.ExternalApiClient;
import com.example.springtdd.Head07_IntegrationTest.repository.OrderRepository;
import com.example.springtdd.Head07_IntegrationTest.repository.ProductRepository;
import com.example.springtdd.Head07_IntegrationTest.repository.UserRepository2;
import com.example.springtdd.Head07_IntegrationTest.service.InventoryService;
import com.example.springtdd.Head07_IntegrationTest.service.OrderService2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = OrderServiceIntegrationTest.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OrderServiceIntegrationTest {

    @Autowired private OrderService2 orderService;
    @Autowired private UserRepository2 userRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private InventoryService inventoryService;

    @MockBean private ExternalApiClient externalApiClient;

    @Test
    @DisplayName("전체 주문 프로세스가 모든 계층에서 올바르게 동작해야 한다")
    void completeOrderProcessIntegration() {
        User2 user = userRepository.save(new User2("test@example.com", "홍길동", UserGrade.GOLD));
        Product product = productRepository.save(new Product("테스트상품", 50000, 20));

        CreateOrderRequest request = CreateOrderRequest.builder()
                .userId(user.getId())
                .items(Arrays.asList(new OrderItemRequest(product.getId(), 3, 50000)))
                .deliveryAddress(new DeliveryAddress("12345", "서울시 강남구", "상세주소"))
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .build();

        OrderResponse response = orderService.createOrder(request);

        Order2 savedOrder = orderRepository.findById(response.getOrderId()).orElseThrow();
        assertEquals(OrderStatus.CONFIRMED, savedOrder.getStatus());
        assertEquals(150000, savedOrder.getTotalAmount());

        Product updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(17, updatedProduct.getStock());

        User2 updatedUser = userRepository.findById(user.getId()).orElseThrow();
        assertEquals(4500, updatedUser.getPoints());
    }

    @Test
    @DisplayName("동시 주문 시나리오에서 재고 관리가 안전하게 처리되어야 한다")
    void concurrentOrderProcessing() throws Exception {
        Product limitedProduct = productRepository.save(new Product("한정상품", 100000, 5));
        List<User2> users = IntStream.range(0, 10)
                .mapToObj(i -> userRepository.save(
                        new User2("user" + i + "@example.com", "사용자" + i, UserGrade.BASIC)))
                .collect(Collectors.toList());

        CountDownLatch latch = new CountDownLatch(10);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<OrderResponse>> futures = new ArrayList<>();

        for (User2 user : users) {
            CompletableFuture<OrderResponse> future = CompletableFuture.supplyAsync(() -> {
                try {
                    latch.countDown();
                    latch.await();

                    CreateOrderRequest request = CreateOrderRequest.builder()
                            .userId(user.getId())
                            .items(Arrays.asList(new OrderItemRequest(limitedProduct.getId(), 1, 100000)))
                            .build();

                    return orderService.createOrder(request);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executor);
            futures.add(future);
        }

        List<OrderResponse> successfulOrders = new ArrayList<>();
        List<Exception> exceptions = new ArrayList<>();

        for (CompletableFuture<OrderResponse> future : futures) {
            try {
                successfulOrders.add(future.get());
            } catch (Exception e) {
                exceptions.add(e);
            }
        }

        assertEquals(5, successfulOrders.size());
        assertEquals(5, exceptions.size());

        Product finalProduct = productRepository.findById(limitedProduct.getId()).orElseThrow();
        assertEquals(0, finalProduct.getStock());

        executor.shutdown();
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories(basePackages = "com.example.springtdd.Head07_IntegrationTest.repository")
    @EntityScan(basePackages = "com.example.springtdd.Head07_IntegrationTest.domain")
    static class TestConfig {

        @Bean
        OrderService2 orderService(
                UserRepository2 userRepository,
                ProductRepository productRepository,
                OrderRepository orderRepository,
                InventoryService inventoryService
        ) {
            return new OrderService2(userRepository, productRepository, orderRepository, inventoryService);
        }

        @Bean
        InventoryService inventoryService(ProductRepository productRepository) {
            return new InventoryService(productRepository);
        }
    }
}