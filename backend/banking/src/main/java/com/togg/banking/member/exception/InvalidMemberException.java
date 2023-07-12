package com.togg.banking.member.exception;

public class InvalidMemberException extends RuntimeException {

    public InvalidMemberException(String message) {
        super(message);
    }

    public InvalidMemberException() {
        this("유효하지 않은 회원입니다.");
    }
}
