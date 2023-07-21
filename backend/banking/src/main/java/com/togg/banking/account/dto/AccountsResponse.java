package com.togg.banking.account.dto;

import java.util.List;

public record AccountsResponse(List<AccountResponse> accounts) {
}
