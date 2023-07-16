package com.togg.banking.account.domain;

import com.togg.banking.common.exception.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByMemberId(Long memberId);

    default Account getByMemberId(Long memberId) {
        return findByMemberId(memberId)
                .orElseThrow(() -> new NoSuchEntityException("해당 회원 아이디의 계좌가 존재하지 않습니다."));
    }

    Optional<Account> findByNumber(String number);

    default Account getByNumber(String number) {
        return findByNumber(number)
                .orElseThrow(() -> new NoSuchEntityException("해당 계좌번호의 계좌가 존재하지 않습니다."));
    }
}
