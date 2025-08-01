package org.example.Head04_Collections.topic04_LambdaExpression.example12_Lambda_advanced_Practice;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class TransactionManager {
    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // Predicate: 조건에 맞는 트랜잭션만 반환
    public List<Transaction> filterTransactions(Predicate<Transaction> predicate) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    // Function: 트랜잭션 금액 변환(매핑)
    public List<Double> mapAmounts(Function<Transaction, Double> function) {
        List<Double> result = new ArrayList<>();
        for (Transaction t : transactions) {
            result.add(function.apply(t));
        }
        return result;
    }

    // Consumer: 트랜잭션 처리(출력, 로깅 등)
    public void processTransactions(Consumer<Transaction> consumer) {
        for (Transaction t : transactions) {
            consumer.accept(t);
        }
    }
}
