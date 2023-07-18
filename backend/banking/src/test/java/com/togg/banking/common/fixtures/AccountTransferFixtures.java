package com.togg.banking.common.fixtures;

import com.togg.banking.account.domain.Account;
import com.togg.banking.account.domain.AccountTransfer;
import com.togg.banking.account.dto.AccountTransferRequest;
import com.togg.banking.account.dto.AccountTransferResponse;

public class AccountTransferFixtures {

    public static final AccountTransferRequest ACCOUNT_TRANSFER_REQUEST = new AccountTransferRequest("100012344321", "100012344321", 500);
    public static final AccountTransferResponse ACCOUNT_TRANSFER_RESPONSE = new AccountTransferResponse("100012345678", "100012344321", 500);

    public static AccountTransfer accountTransfer(Account giverAccount, Account receiverAccount, int amount) {
        AccountTransfer accountTransfer = new AccountTransfer(giverAccount, receiverAccount, amount);
        accountTransfer.changeReceiver(receiverAccount);
        accountTransfer.changeGiver(giverAccount);
        return accountTransfer;
    }
}
