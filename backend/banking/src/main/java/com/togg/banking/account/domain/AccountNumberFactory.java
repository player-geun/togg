package com.togg.banking.account.domain;

import java.util.Random;

public class AccountNumberFactory {

    private final static String BANKBOOK_NUMBER = "1000";
    private final static int MIN_NUMBER = 10_000_000;
    private final static Random RANDOM = new Random();

    public static String createNumber() {
        int number = RANDOM.nextInt(90_000_000) + MIN_NUMBER;
        return BANKBOOK_NUMBER + number;
    }
}
