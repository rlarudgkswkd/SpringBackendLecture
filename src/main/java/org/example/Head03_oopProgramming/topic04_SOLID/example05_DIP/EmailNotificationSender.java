package org.example.Head03_oopProgramming.topic04_SOLID.example05_DIP;

public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("이메일 전송: " + message);
    }
}
