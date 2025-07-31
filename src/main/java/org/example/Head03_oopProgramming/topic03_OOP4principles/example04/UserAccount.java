package org.example.Head03_oopProgramming.topic03_OOP4principles.example04;

public class UserAccount {
    private final String accountId;   // final: 한 번 설정되면 변경 불가
    private double balance;

    public UserAccount(String accountId) {
        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("계좌 ID는 필수입니다.");
        }
        this.accountId = accountId;
        this.balance = 0;
    }

    // 읽기 전용 메서드 (getter)
    public String getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    // 쓰기 메서드: 내부 검증 포함
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("입금 금액은 0보다 커야 합니다.");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("출금 금액은 0보다 커야 합니다.");
        }
        if (balance < amount) {
            throw new IllegalStateException("잔액 부족: 현재 잔액 = " + balance);
        }
        balance -= amount;
    }
}