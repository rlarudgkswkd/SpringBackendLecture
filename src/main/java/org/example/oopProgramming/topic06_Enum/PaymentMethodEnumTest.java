package org.example.oopProgramming.topic06_Enum;

public class PaymentMethodEnumTest {
    public enum PaymentMethod {
        CREDIT_CARD("신용카드"),
        ACCOUNT_TRANSFER("계좌이체"),
        MOBILE_PAYMENT("모바일결제");

        private final String displayName;

        PaymentMethod(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public static void main(String[] args) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            System.out.println(paymentMethod.getDisplayName());
        }

        PaymentMethod method = PaymentMethod.CREDIT_CARD;
        switch (method) {
            case CREDIT_CARD:
                System.out.println("신용카드 결제 선택");
                break;
            case ACCOUNT_TRANSFER:
                System.out.println("계좌이체 결제 선택");
                break;
            case MOBILE_PAYMENT:
                System.out.println("모바일 결제 선택");
                break;
            default:
                break;
        }
    }
}
