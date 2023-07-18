package com.togg.banking.account.domain;

import com.togg.banking.account.exception.InvalidAccountException;
import com.togg.banking.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Account {

    private final static int NUMBER_LENGTH = 12;
    private final static int INITIAL_BALANCE = 1_000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private int balance;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransfer> givenAccountTransfers = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransfer> receivedAccountTransfers = new ArrayList<>();

    public Account(Member member, String number) {
        validateNumberLength(number);
        this.member = member;
        this.number = number;
        this.balance = INITIAL_BALANCE;
    }

    private void validateNumberLength(String number) {
        if (number.length() != NUMBER_LENGTH) {
            throw new InvalidAccountException("계좌번호의 길이가 유효하지 않습니다. : " + number);
        }
    }

    public int deposit(int amount) {
        return this.balance += amount;
    }

    public int withdraw(int amount) {
        validateBalance(amount);
        return this.balance -= amount;
    }

    private void validateBalance(int amount) {
        if (balance < amount) {
            throw new InvalidAccountException("현재 계좌 잔액보다 많은 돈을 출금할 수 없습니다.");
        }
    }
}
