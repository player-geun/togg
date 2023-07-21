package com.togg.banking.account.domain;

import com.togg.banking.common.exception.NoSuchEntityException;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT DISTINCT a from Account a LEFT JOIN FETCH a.givenAccountTransfers WHERE a.number =:number")
    Optional<Account> findByNumberWithGivenAccountTransfers(String number);

    default Account getByNumberWithGivenAccountTransfers(String number) {
        return findByNumberWithGivenAccountTransfers(number)
                .orElseThrow(() -> new NoSuchEntityException("해당 계좌번호의 계좌가 존재하지 않습니다."));
    }

    @Query("SELECT DISTINCT a from Account a LEFT JOIN FETCH a.receivedAccountTransfers WHERE a.number =:number")
    Optional<Account> findByNumberWithReceivedAccountTransfers(String number);

    default Account getByNumberWithReceivedAccountTransfers(String number) {
        return findByNumberWithReceivedAccountTransfers(number)
                .orElseThrow(() -> new NoSuchEntityException("해당 계좌번호의 계좌가 존재하지 않습니다."));
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT DISTINCT a from Account a WHERE a.number =:number")
    Optional<Account> findByNumberWithLock(String number);

    default Account getByNumberWithGivenAccountTransfersAndLock(String number) {
        return findByNumberWithLock(number)
                .orElseThrow(() -> new NoSuchEntityException("해당 계좌번호의 계좌가 존재하지 않습니다."));
    }

    default Account getByNumberWithReceivedAccountTransfersAndLock(String number) {
        return findByNumberWithLock(number)
                .orElseThrow(() -> new NoSuchEntityException("해당 계좌번호의 계좌가 존재하지 않습니다."));
    }

    List<Account> findByMemberId(Long memberId);
}
