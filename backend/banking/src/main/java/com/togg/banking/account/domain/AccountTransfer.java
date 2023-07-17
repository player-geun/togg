package com.togg.banking.account.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AccountTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_giver_id", nullable = false)
    private Account giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_receiver_id", nullable = false)
    private Account receiver;

    @Column(nullable = false)
    private int amount;

    public AccountTransfer(Account giver, Account receiver, int amount) {
        this.giver = giver;
        this.receiver = receiver;
        this.amount = amount;
    }

    public void transfer() {
        giver.withdraw(amount);
        receiver.deposit(amount);
    }
}
