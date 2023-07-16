package com.togg.banking.account.exception;

public class InvalidAccountException extends RuntimeException {

    public InvalidAccountException(String message) {
        super(message);
    }

    public InvalidAccountException() {
        this("유효하지 않은 계좌입니다.");
    }
}
