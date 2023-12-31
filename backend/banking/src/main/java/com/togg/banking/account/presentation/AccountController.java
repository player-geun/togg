package com.togg.banking.account.presentation;

import com.togg.banking.account.application.AccountService;
import com.togg.banking.account.dto.*;
import com.togg.banking.auth.dto.LoginMember;
import com.togg.banking.member.application.MemberService;
import com.togg.banking.member.domain.Member;
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
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @AuthenticationPrincipal LoginMember member) {
        Member foundMember = memberService.findByIdForOtherTransaction(member.id());
        AccountResponse response = accountService.save(foundMember);
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

    @GetMapping("/{accountNumber}/transfers")
    public ResponseEntity<AccountTransfersResponse> findAccountTransfersByAccountNumber(
            @PathVariable String accountNumber) {
        AccountTransfersResponse response =
                accountService.findAccountTransfersByAccountNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AccountsResponse> findMyAccounts(@AuthenticationPrincipal LoginMember member) {
        AccountsResponse response = accountService.findMyAccounts(member.id());
        return ResponseEntity.ok(response);
    }
}
