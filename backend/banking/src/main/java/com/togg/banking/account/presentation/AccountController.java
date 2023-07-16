package com.togg.banking.account.presentation;

import com.togg.banking.account.application.AccountService;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.auth.dto.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@AuthenticationPrincipal LoginMember member) {
        AccountResponse response = accountService.create(member.id());
        return ResponseEntity.created(URI.create("/api/accounts/" + response.id())).body(response);
    }
}
