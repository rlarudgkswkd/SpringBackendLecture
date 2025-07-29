package org.example.oopProgramming.topic04_SOLID.example05_DIP;

public class SmsNotificationSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("SMS 전송: " + message);
    }
}
