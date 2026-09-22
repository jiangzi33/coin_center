package com.example.coin_center.exception;

public class CoinNotEnoughException extends RuntimeException {
    public CoinNotEnoughException(String message) {
        super(message);
    }
}
