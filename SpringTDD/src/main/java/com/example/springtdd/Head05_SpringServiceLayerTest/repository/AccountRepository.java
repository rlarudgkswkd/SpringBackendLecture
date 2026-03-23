package com.example.springtdd.Head05_SpringServiceLayerTest.repository;

import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
