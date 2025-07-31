package org.example.Head03_oopProgramming.topic05_InnerClassAndAnonymousClass.example04_AnonymousClass;

public class AnonymousClassExample {

    public static void main(String[] args) {
        // 익명 클래스 사용
        EventListener listener = new EventListener() {
            @Override
            public void onEvent(String eventData) {
                System.out.println("이벤트 발생: " + eventData);
            }
        };

        simulateEvent("USER_LOGIN", listener);
    }

    public static void simulateEvent(String eventData, EventListener listener) {
        listener.onEvent(eventData);
    }
}
