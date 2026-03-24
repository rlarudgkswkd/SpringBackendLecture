package com.example.springtdd.Head07_IntegrationTest.repository;

import com.example.springtdd.Head07_IntegrationTest.domain.order.Order2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order2, Long> {
}
