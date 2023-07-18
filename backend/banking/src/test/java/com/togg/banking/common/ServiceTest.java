package com.togg.banking.common;

import com.togg.banking.account.application.AccountService;
import com.togg.banking.account.domain.AccountRepository;
import com.togg.banking.member.application.MemberService;
import com.togg.banking.member.domain.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceTest {

    @InjectMocks
    protected AccountService accountService;

    @InjectMocks
    protected MemberService memberService;

    @Mock
    protected AccountRepository accountRepository;

    @Mock
    protected MemberRepository memberRepository;

}
