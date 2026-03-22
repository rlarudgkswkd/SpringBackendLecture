package com.example.springtdd.Head04_MockitoBasic.service;

import java.util.concurrent.CompletableFuture;

public interface AsyncEmailService {

    CompletableFuture<Boolean> sendAsync(String email, String message);
}
