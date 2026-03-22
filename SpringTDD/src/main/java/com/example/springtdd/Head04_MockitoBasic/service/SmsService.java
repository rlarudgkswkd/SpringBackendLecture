package com.example.springtdd.Head04_MockitoBasic.service;

import java.util.List;

public interface SmsService {

    boolean send(String message);

    boolean sendBatch(List<String> messages);
}
