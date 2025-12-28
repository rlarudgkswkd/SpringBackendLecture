package com.example.coffeeorder.repository;

import com.example.coffeeorder.domain.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {}