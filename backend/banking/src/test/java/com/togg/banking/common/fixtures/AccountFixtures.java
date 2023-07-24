package com.togg.banking.common.fixtures;

import com.togg.banking.account.domain.Account;
import com.togg.banking.account.dto.AccountResponse;

public class AccountFixtures {

    public static final int INITIAL_BALANCE = 1_000;
    public static final String ACCOUNT_NUMBER = "100012345678";
    public static final AccountResponse ACCOUNT_RESPONSE = new AccountResponse(1L, ACCOUNT_NUMBER, 1000);

    public static Account account() {
        return new Account(MemberFixtures.member(), ACCOUNT_NUMBER);
    }

    public static Account receiverAccount() {
        return new Account(MemberFixtures.receiver(), "100012345678");
    }
}
