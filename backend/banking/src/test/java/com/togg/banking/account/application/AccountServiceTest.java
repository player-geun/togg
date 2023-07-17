package com.togg.banking.account.application;

import com.togg.banking.account.domain.Account;
import com.togg.banking.account.domain.AccountTransfer;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.account.dto.AccountTransferRequest;
import com.togg.banking.account.dto.AccountTransferResponse;
import com.togg.banking.common.ServiceTest;
import com.togg.banking.common.fixtures.AccountFixtures;
import com.togg.banking.common.fixtures.AccountTransferFixtures;
import com.togg.banking.common.fixtures.MemberFixtures;
import com.togg.banking.member.domain.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class AccountServiceTest extends ServiceTest {

    @Test
    void 계좌를_등록한다() {
        // given
        Member member = MemberFixtures.member();
        Account account = AccountFixtures.account();

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
        AccountTransferRequest request = AccountTransferFixtures.ACCOUNT_TRANSFER_REQUEST;
        Account giverAccount = AccountFixtures.account();
        Account receiverAccount = AccountFixtures.receiverAccount();
        AccountTransfer accountTransfer = AccountTransferFixtures.accountTransfer(500);

        given(accountRepository.getByMemberIdWithLock(any())).willReturn(giverAccount);
        given(accountRepository.getByNumberWithLock(any())).willReturn(receiverAccount);
        given(accountTransferRepository.save(any(AccountTransfer.class))).willReturn(accountTransfer);

        // when
        AccountTransferResponse result = accountService.transfer(1L, request);

        // then
        assertThat(result.amount()).isEqualTo(500);
    }
}
