package com.togg.banking.common.fixtures;

import com.togg.banking.account.domain.Account;
import com.togg.banking.account.dto.AccountResponse;

public class AccountFixtures {

    public static final AccountResponse ACCOUNT_RESPONSE = new AccountResponse(1L, "100012345678", 1000);

    public static Account account() {
        return new Account(MemberFixtures.member(), "100012345678");
    }

    public static Account receiverAccount() {
        return new Account(MemberFixtures.receiver(), "100012345678");
    }
}
