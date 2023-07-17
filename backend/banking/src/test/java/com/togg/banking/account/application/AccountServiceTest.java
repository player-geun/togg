package com.togg.banking.account.application;

import com.togg.banking.account.domain.Account;
import com.togg.banking.account.domain.AccountTransfer;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.account.dto.AccountTransferRequest;
import com.togg.banking.account.dto.AccountTransferResponse;
import com.togg.banking.common.ServiceTest;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.Role;
import com.togg.banking.member.domain.SocialType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class AccountServiceTest extends ServiceTest {

    @Test
    void 계좌를_등록한다() {
        // given
        Member member = new Member("이근우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "1");
        Account account = new Account(member, "100012345678");

        given(memberRepository.getById(any())).willReturn(member);
        given(accountRepository.save(any())).willReturn(account);

        // when
        AccountResponse result = accountService.create(1L);

        // then
        assertThat(result.balance()).isEqualTo(1000);
    }
    
    @Test
    void 계좌이체를_한다() {
        // given
        AccountTransferRequest request = new AccountTransferRequest("100012343211", 500);
        Member giver = new Member("이근우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "1");
        Account giverAccount = new Account(giver, "100012345678");
        Member receiver = new Member("이준우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "2");
        Account receiverAccount = new Account(receiver, "100012343211");
        AccountTransfer accountTransfer = new AccountTransfer(giverAccount, receiverAccount, 500);

        given(accountRepository.getByMemberIdWithLock(any())).willReturn(giverAccount);
        given(accountRepository.getByNumberWithLock(any())).willReturn(receiverAccount);
        given(accountTransferRepository.save(any(AccountTransfer.class))).willReturn(accountTransfer);

        // when
        AccountTransferResponse result = accountService.transfer(1L, request);

        // then
        assertThat(result.amount()).isEqualTo(500);
    }
}
