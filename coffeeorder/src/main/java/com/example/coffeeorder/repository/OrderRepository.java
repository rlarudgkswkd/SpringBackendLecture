package com.example.coffeeorder.repository;

import com.example.coffeeorder.domain.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 주문 조회 시 커피까지 필요 → N+1 방지용
    @EntityGraph(attributePaths = {"member", "orderCoffees", "orderCoffees.coffee"})
    Optional<Order> findById(Long id);

    @EntityGraph(attributePaths = {"member", "orderCoffees", "orderCoffees.coffee"})
    List<Order> findAll();
}