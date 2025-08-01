package org.example.Head04_Collections.topic04_LambdaExpression.example02;

public class LambdaCaptureDemo {
    private String memberField = "Outer Field";

    public void testLambda() {
        String localVar = "Local variable";

        Runnable r = () -> {
            // 외부 클래스의 인스턴스 필드
            System.out.println("memberField = " + memberField);

            // 메서드 내부에 선언된 지역 변수 (effective final)
            System.out.println("localVar = " + localVar);

            // localVar = "Change"; // 컴파일 에러 - 캡처된 localVar 변경 불가
        };

        r.run();
    }

    public static void main(String[] args) {
        new LambdaCaptureDemo().testLambda();
    }
}
