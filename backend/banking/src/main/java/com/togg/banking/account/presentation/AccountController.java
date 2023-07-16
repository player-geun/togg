package com.togg.banking.account.presentation;

import com.togg.banking.account.application.AccountService;
import com.togg.banking.account.dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<AccountResponse> create(@AuthenticationPrincipal UserDetails user) {
        String email = user.getUsername();
        AccountResponse response = accountService.create(email);
        return ResponseEntity.created(URI.create("/api/accounts/" + response.id())).body(response);
    }
}
