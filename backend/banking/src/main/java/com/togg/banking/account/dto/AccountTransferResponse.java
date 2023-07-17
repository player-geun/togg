package com.togg.banking.account.dto;

import com.togg.banking.account.domain.AccountTransfer;

public record AccountTransferResponse(String giverAccountNumber,
                                      String receiverAccountNumber,
                                      int amount) {
    public AccountTransferResponse(AccountTransfer accountTransfer) {
        this(
                accountTransfer.getGiver().getNumber(),
                accountTransfer.getReceiver().getNumber(),
                accountTransfer.getAmount());
    }
}
