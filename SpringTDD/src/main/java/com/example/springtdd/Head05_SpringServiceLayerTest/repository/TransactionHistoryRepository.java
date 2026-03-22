package com.example.springtdd.Head05_SpringServiceLayerTest.repository;

import com.example.springtdd.Head05_SpringServiceLayerTest.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> findBySenderAccountId(Long senderId);
}
