package com.example.coffeeorder.repository;

import com.example.coffeeorder.domain.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampRepository extends JpaRepository<Stamp, Long> {
    Optional<Stamp> findByMemberId(Long memberId);
}