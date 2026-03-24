package com.example.springtdd.Head05_SpringServiceLayerTest.example01;

import com.example.springtdd.Head04_MockitoBasic.service.AuditService;
import com.example.springtdd.Head04_MockitoBasic.service.EmailService;
import com.example.springtdd.Head04_MockitoBasic.service.PasswordEncoder;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.Account;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.AccountStatus;
import com.example.springtdd.Head05_SpringServiceLayerTest.entity.TransactionHistory;
import com.example.springtdd.Head05_SpringServiceLayerTest.exception.InactiveAccountException;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.AccountRepository;
import com.example.springtdd.Head05_SpringServiceLayerTest.repository.TransactionHistoryRepository;
import com.example.springtdd.Head05_SpringServiceLayerTest.service.AccountService;
import com.example.springtdd.Head06_ControllerTest.service.AdminService;
import com.example.springtdd.Head06_ControllerTest.service.OrderService;
import com.example.springtdd.Head06_ControllerTest.service.ProductService;
import com.example.springtdd.Head06_ControllerTest.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TransactionBehaviorTest {

    @Autowired private AccountService accountService;
    @Autowired private AccountRepository accountRepository;
    @Autowired private TransactionHistoryRepository historyRepository;

    @MockBean private EmailService emailService;
    @MockBean private AuditService auditService;
    @MockBean private PasswordEncoder passwordEncoder;
    @MockBean private AdminService adminService;
    @MockBean private OrderService orderService;
    @MockBean private ProductService productService;
    @MockBean private UserService userService;

    @Test
    @DisplayName("계좌 이체 중 오류 발생시 모든 변경사항이 롤백되어야 한다")
    void transferRollbackTest() {

        // Given
        Account sender = accountRepository.save(new Account("sender", 100000));
        Account receiver = accountRepository.save(new Account("receiver", 50000));

        receiver.setStatus(AccountStatus.INACTIVE);
        accountRepository.save(receiver);

        // When & Then
        assertThrows(InactiveAccountException.class, () -> {
            accountService.transfer(sender.getId(), receiver.getId(), 30000);
        });

        Account unchangedSender = accountRepository.findById(sender.getId()).orElseThrow();
        assertEquals(100000, unchangedSender.getBalance());

        List<TransactionHistory> histories =
                historyRepository.findBySenderAccountId(sender.getId());
        assertTrue(histories.isEmpty());
    }

    @Test
    //테스트 실패됨. 실패 후 롤백이 되었는지를 확인해야함
    @DisplayName("중첩 트랜잭션 실패시 전체 롤백")
    void nestedTransactionTest() {

        Account account = accountRepository.save(new Account("test", 100000));

        assertThrows(DataIntegrityViolationException.class, () -> {
            accountService.performComplexOperation(account.getId());
        });

        Account unchanged = accountRepository.findById(account.getId()).orElseThrow();
        assertEquals(100000, unchanged.getBalance());
        assertEquals(AccountStatus.ACTIVE, unchanged.getStatus());
    }
}