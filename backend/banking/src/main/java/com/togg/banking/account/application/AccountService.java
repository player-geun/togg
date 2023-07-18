package com.togg.banking.account.application;

import com.togg.banking.account.domain.*;
import com.togg.banking.account.dto.AccountResponse;
import com.togg.banking.account.dto.AccountTransferRequest;
import com.togg.banking.account.dto.AccountTransferResponse;
import com.togg.banking.account.dto.AccountTransfersResponse;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
