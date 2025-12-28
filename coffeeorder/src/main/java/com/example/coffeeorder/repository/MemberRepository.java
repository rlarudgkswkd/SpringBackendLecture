package com.example.coffeeorder.repository;

import com.example.coffeeorder.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {}