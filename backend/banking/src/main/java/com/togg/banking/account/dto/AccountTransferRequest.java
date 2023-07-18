package com.togg.banking.account.dto;

public record AccountTransferRequest(String giverAccountNumber, String receiverAccountNumber, int amount) {
}
