package com.example.coffeeorder.service;

import com.example.coffeeorder.domain.*;
import com.example.coffeeorder.dto.*;
import com.example.coffeeorder.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StampRepository stampRepository;
    private final CoffeeRepository coffeeRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Stamp stamp = stampRepository.findByMemberId(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("스탬프 없음"));

        Order order = new Order(member);

        int totalQty = 0;

        for (OrderCoffeeRequest ocReq : request.orderCoffees()) {
            if (ocReq.quantity() <= 0) throw new IllegalArgumentException("quantity는 1 이상");

            Coffee coffee = coffeeRepository.findById(ocReq.coffeeId())
                    .orElseThrow(() -> new IllegalArgumentException("커피 없음: " + ocReq.coffeeId()));

            totalQty += ocReq.quantity();

            OrderCoffee oc = new OrderCoffee(coffee, ocReq.quantity());
            order.addOrderCoffee(oc);
        }

        stamp.increase(totalQty);

        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 없음"));
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private OrderResponse toResponse(Order order) {
        List<OrderCoffeeResponse> coffees = order.getOrderCoffees().stream()
                .map(oc -> new OrderCoffeeResponse(
                        oc.getCoffee().getId(),
                        oc.getCoffee().getName(),
                        oc.getCoffee().getPrice(),
                        oc.getQuantity()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getMember().getId(),
                coffees
        );
    }
}