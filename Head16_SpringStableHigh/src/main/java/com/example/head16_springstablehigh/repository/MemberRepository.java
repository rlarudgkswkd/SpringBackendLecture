package com.example.head16_springstablehigh.repository;

import com.example.head16_springstablehigh.entity.MemberTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberTest, Long> {
}