package com.togg.banking.account.dto;

import java.util.List;

public record AccountTransfersResponse(List<AccountTransferResponse> givenAccountTransfers,
                                       List<AccountTransferResponse> receivedAccountTransfers) {
}
