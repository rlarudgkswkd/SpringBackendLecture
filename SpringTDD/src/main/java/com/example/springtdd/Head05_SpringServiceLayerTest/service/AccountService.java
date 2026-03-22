package com.example.springtdd.Head05_SpringServiceLayerTest.service;

import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Account;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.AccountStatus;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.TransactionHistory;
import com.example.springtdd.Head05_SpringServiceLayerTest.exception.InactiveAccountException;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.AccountRepository;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionHistoryRepository historyRepository;

    @Transactional
    public void transfer(Long senderId, Long receiverId, int amount) {

        Account sender = accountRepository.findById(senderId).orElseThrow();
        Account receiver = accountRepository.findById(receiverId).orElseThrow();

        if (receiver.getStatus() == AccountStatus.INACTIVE) {
            throw new InactiveAccountException("비활성 계좌");
        }

        sender.withdraw(amount);
        receiver.deposit(amount);

        historyRepository.save(new TransactionHistory(senderId, receiverId, amount));
    }

    @Transactional
    public void performComplexOperation(Long accountId) {

        Account account = accountRepository.findById(accountId).orElseThrow();

        account.withdraw(1000);

        // 강제로 실패 발생 → 트랜잭션 롤백 테스트용
        throw new DataIntegrityViolationException("강제 실패");
    }
}
