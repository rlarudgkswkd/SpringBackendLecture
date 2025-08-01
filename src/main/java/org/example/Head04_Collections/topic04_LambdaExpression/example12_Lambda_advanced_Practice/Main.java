package org.example.Head04_Collections.topic04_LambdaExpression.example12_Lambda_advanced_Practice;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();
        Scanner sc = new Scanner(System.in);

        // Supplier: 임의의 트랜잭션 생성
        Supplier<Transaction> randomTransactionSupplier = () -> {
            // 간단히 ID는 난수, type은 고정, amount도 난수
            int randId = (int)(Math.random() * 1000);
            double randAmount = Math.random() * 100000;
            return new Transaction(randId, "PAYMENT", randAmount);
        };

        boolean run = true;
        while (run) {
            System.out.println("\\n=== 메뉴 ===");
            System.out.println("1. 트랜잭션 수동 추가");
            System.out.println("2. 트랜잭션 임의(Supplier) 추가");
            System.out.println("3. 특정 유형 필터링(Predicate)");
            System.out.println("4. 금액 변환(Function) 결과 보기");
            System.out.println("5. 모든 트랜잭션 출력(Consumer)");
            System.out.println("6. 종료");
            System.out.print("선택: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ID 입력: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("유형 입력(PAYMENT/REFUND 등): ");
                    String type = sc.nextLine();
                    System.out.print("금액 입력: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    Transaction t = new Transaction(id, type, amount);
                    manager.addTransaction(t);
                    System.out.println("[Info] 트랜잭션이 추가되었습니다.");
                    break;

                case 2:
                    // Supplier 이용
                    Transaction randT = randomTransactionSupplier.get();
                    manager.addTransaction(randT);
                    System.out.println("[Info] 임의 트랜잭션 추가: " + randT);
                    break;

                case 3:
                    // Predicate 이용
                    System.out.print("필터링할 유형 입력: ");
                    String filterType = sc.nextLine();
                    Predicate<Transaction> byType = tran -> tran.getType().equalsIgnoreCase(filterType);

                    List<Transaction> filtered = manager.filterTransactions(byType);
                    System.out.println("[결과] 필터링된 트랜잭션: " + filtered);
                    break;

                case 4:
                    // Function 이용
                    System.out.print("할인율(%) 입력: ");
                    double discountPercent = sc.nextDouble();
                    sc.nextLine();

                    Function<Transaction, Double> discountFunc = tran -> tran.getAmount() * (1 - (discountPercent / 100.0));
                    List<Double> discountedAmounts = manager.mapAmounts(discountFunc);
                    System.out.println("[결과] 변환된 금액 목록: " + discountedAmounts);
                    break;

                case 5:
                    // Consumer 이용
                    Consumer<Transaction> printTran = tran -> System.out.println("[Tx] " + tran);
                    manager.processTransactions(printTran);
                    break;

                case 6:
                    run = false;
                    System.out.println("[Info] 종료합니다.");
                    break;

                default:
                    System.out.println("[Error] 잘못된 입력입니다.");
            }
        }

        sc.close();
    }
}

