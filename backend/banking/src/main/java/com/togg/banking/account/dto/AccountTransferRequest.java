package com.togg.banking.account.dto;

public record AccountTransferRequest(String receiverAccountNumber, int amount) {
}
