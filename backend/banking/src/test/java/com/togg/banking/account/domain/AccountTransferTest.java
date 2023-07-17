package com.togg.banking.account.domain;

import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.Role;
import com.togg.banking.member.domain.SocialType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountTransferTest {

    @Test
    void 계좌이체를_한다() {
        // given
        Member giver = new Member("이근우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "1");
        Member receiver = new Member("이준우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "2");
        Account giverAccount = new Account(giver, "100012345678");
        Account receiverAccount = new Account(receiver, "100012343211");
        AccountTransfer accountTransfer = new AccountTransfer(giverAccount, receiverAccount, 500);

        // when
        accountTransfer.transfer();

        // then
        assertThat(receiverAccount.getBalance()).isEqualTo(1500);
    }
}
