package com.example.coin_center.exception;

public class CoinNotExistException extends RuntimeException {
    public CoinNotExistException(String message) {
        super(message);
    }
}
