package com.example.springtdd.Head05_SpringServiceLayerTest.exception;

public class InactiveAccountException extends RuntimeException {
    public InactiveAccountException(String message) {
        super(message);
    }
}
