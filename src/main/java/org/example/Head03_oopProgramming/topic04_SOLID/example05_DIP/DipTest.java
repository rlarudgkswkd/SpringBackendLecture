package org.example.Head03_oopProgramming.topic04_SOLID.example05_DIP;

public class DipTest {
    public static void main(String[] args) {
        NotificationSender[] notificationSenders = new NotificationSender[]{
                new EmailNotificationSender(),
                new SmsNotificationSender()
        };
        for (NotificationSender notificationSender : notificationSenders) {
            NotificationService notificationService = new NotificationService(notificationSender);
            notificationService.notify("Hello World");
        }
    }
}
