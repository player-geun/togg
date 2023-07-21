package com.togg.banking.account.application;

import com.togg.banking.account.domain.Account;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.account.dto.AccountTransferRequest;
import com.togg.banking.account.dto.AccountTransferResponse;
import com.togg.banking.account.dto.AccountTransfersResponse;
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
    void 계좌를_저장한다() {
        // given
        Member member = MemberFixtures.member();
        Account account = AccountFixtures.account();

        given(accountRepository.save(any())).willReturn(account);

        // when
        AccountResponse result = accountService.save(member);

        // then
        assertThat(result.balance()).isEqualTo(AccountFixtures.INITIAL_BALANCE);
    }
    
    @Test
    void 계좌이체를_한다() {
        // given
        AccountTransferRequest request = AccountTransferFixtures.ACCOUNT_TRANSFER_REQUEST;
        Account giverAccount = AccountFixtures.account();
        Account receiverAccount = AccountFixtures.receiverAccount();
        int initialGiverAccountBalance = giverAccount.getBalance();

        given(accountRepository.getByNumberWithGivenAccountTransfersAndLock(any())).willReturn(giverAccount);
        given(accountRepository.getByNumberWithReceivedAccountTransfersAndLock(any())).willReturn(receiverAccount);

        // when
        AccountTransferResponse result = accountService.transfer(request);

        // then
        assertThat(result.amount()).isEqualTo(initialGiverAccountBalance - request.amount());
    }

    @Test
    void 계좌이체내역을_조회한다() {
        // given
        Account giverAccount = AccountFixtures.account();
        Account receiverAccount = AccountFixtures.receiverAccount();
        AccountTransferFixtures.accountTransfer(giverAccount, receiverAccount, 500);

        given(accountRepository.getByNumberWithGivenAccountTransfers(any())).willReturn(giverAccount);
        given(accountRepository.getByNumberWithReceivedAccountTransfers(any())).willReturn(receiverAccount);

        // when
        AccountTransfersResponse response = accountService.findAccountTransfersByAccountNumber(
                AccountFixtures.ACCOUNT_NUMBER);

        // then
        assertThat(response.givenAccountTransfers()).hasSize(1);
    }
}
