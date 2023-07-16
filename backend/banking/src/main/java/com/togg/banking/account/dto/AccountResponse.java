package com.togg.banking.account.dto;

import com.togg.banking.account.domain.Account;

public record AccountResponse(Long id, String number, int balance) {

    public AccountResponse(Account account) {
        this(account.getId(), account.getNumber(), account.getBalance());
    }
}
