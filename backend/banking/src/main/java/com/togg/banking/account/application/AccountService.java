package com.togg.banking.account.application;

import com.togg.banking.account.domain.*;
import com.togg.banking.account.dto.*;
import com.togg.banking.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    public AccountResponse save(Member member) {
        String number = AccountNumberFactory.createNumber();
        Account account = new Account(member, number);
        Account savedAccount = accountRepository.save(account);
        return new AccountResponse(savedAccount);
    }

    @Transactional
    public AccountTransferResponse transfer(AccountTransferRequest request) {
        Account giver = accountRepository.getByNumberWithGivenAccountTransfersAndLock(
                request.giverAccountNumber());
        Account receiver = accountRepository.getByNumberWithReceivedAccountTransfersAndLock(
                request.receiverAccountNumber());

        AccountTransfer accountTransfer = new AccountTransfer(giver, receiver, request.amount());
        accountTransfer.transfer();
        accountTransfer.changeGiver(giver);
        accountTransfer.changeReceiver(receiver);
        return new AccountTransferResponse(accountTransfer);
    }

    @Transactional(readOnly = true)
    public AccountTransfersResponse findAccountTransfersByAccountNumber(String accountNumber) {
        Account giver = accountRepository.getByNumberWithGivenAccountTransfers(accountNumber);
        Account receiver = accountRepository.getByNumberWithReceivedAccountTransfers(accountNumber);

        List<AccountTransferResponse> givenAccountTransfers = giver.getGivenAccountTransfers().stream()
                .map(AccountTransferResponse::new)
                .toList();
        List<AccountTransferResponse> receivedAccountTransfers = receiver.getReceivedAccountTransfers().stream()
                .map(AccountTransferResponse::new)
                .toList();
        return new AccountTransfersResponse(givenAccountTransfers, receivedAccountTransfers);
    }

    public AccountsResponse findMyAccounts(Long memberId) {
        List<Account> accounts = accountRepository.findByMemberId(memberId);
        List<AccountResponse> responses = accounts.stream().map(AccountResponse::new).toList();
        return new AccountsResponse(responses);
    }
}
