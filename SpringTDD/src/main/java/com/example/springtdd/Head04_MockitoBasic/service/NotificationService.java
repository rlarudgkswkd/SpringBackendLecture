package com.example.springtdd.Head04_MockitoBasic.service;

import java.util.List;

public interface NotificationService {

    boolean send(String message);

    boolean sendBatch(List<String> messages);
}
