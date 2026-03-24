package com.example.springtdd.Head07_IntegrationTest.repository;

import com.example.springtdd.Head07_IntegrationTest.domain.user.User2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository2 extends JpaRepository<User2, Long> {
}
