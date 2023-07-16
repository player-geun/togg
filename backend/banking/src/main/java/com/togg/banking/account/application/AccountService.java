package com.togg.banking.account.application;

import com.togg.banking.account.domain.*;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    public AccountResponse create(Long id) {
        Member member = memberRepository.getById(id);
        Account account = save(member);
        return new AccountResponse(account);
    }

    @Transactional
    public Account save(Member member) {
        String number = AccountNumberFactory.createNumber();
        Account account = new Account(member, number);
        return accountRepository.save(account);
    }
}
