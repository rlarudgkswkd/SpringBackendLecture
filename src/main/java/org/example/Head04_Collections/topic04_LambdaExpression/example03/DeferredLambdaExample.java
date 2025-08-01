package org.example.Head04_Collections.topic04_LambdaExpression.example03;

public class DeferredLambdaExample {
    public static void main(String[] args) {
        // createRunnable() 호출이 끝난 후에 Runnable 객체를 가져온 뒤 실행
        Runnable r = createRunnable();
        // 메서드가 끝난 뒤에도 r.run()을 통해 람다를 호출할 수 있다
        r.run();
    }

    public static Runnable createRunnable() {
        String localVar = "Hello, I'm a local variable!";

        return () -> {
            // 메서드가 이미 종료되었지만, localVar를 캡처해 사용하는 람다
            System.out.println(localVar);
        };
    }
}
