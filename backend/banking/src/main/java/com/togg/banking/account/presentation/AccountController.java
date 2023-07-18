package com.togg.banking.account.presentation;

import com.togg.banking.account.application.AccountService;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.account.dto.AccountTransferRequest;
import com.togg.banking.account.dto.AccountTransferResponse;
import com.togg.banking.account.dto.AccountTransfersResponse;
import com.togg.banking.auth.dto.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @AuthenticationPrincipal LoginMember member) {
        AccountResponse response = accountService.create(member.id());
        return ResponseEntity
                .created(URI.create("/api/accounts/" + response.id()))
                .body(response);
    }

    @PostMapping("/transfers")
    public ResponseEntity<AccountTransferResponse> transfer(
            @RequestBody AccountTransferRequest request) {
        AccountTransferResponse response = accountService.transfer(request);
        return ResponseEntity
                .created(URI.create("/api/accounts/" + response.giverAccountNumber()))
                .body(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountTransfersResponse> findAccountTransfersByAccountNumber(
            @PathVariable String accountNumber) {
        AccountTransfersResponse response =
                accountService.findAccountTransfersByAccountNumber(accountNumber);
        return ResponseEntity.ok(response);
    }
}
