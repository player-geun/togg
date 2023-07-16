package com.togg.banking.account.domain;

import com.togg.banking.account.exception.InvalidAccountException;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.Role;
import com.togg.banking.member.domain.SocialType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    private final static Member MEMBER = new Member("이근우", "g@gamil.com", Role.MEMBER, SocialType.KAKAO, "1");
    private final static String NUMBER = "100012345678";

    @Test
    void 계좌번호의_길이가_유효하지_않으면_예외가_발생한다() {
        // given
        String number = "1234";

        // when & then
        assertThatThrownBy(() -> new Account(MEMBER, number))
                .isInstanceOf(InvalidAccountException.class)
                .hasMessage("계좌번호의 길이가 유효하지 않습니다. : " + number);
    }

    @Test
    void 출금한다() {
        // given
        Account account = new Account(MEMBER, NUMBER);

        // when
        int result = account.withdraw(100);

        // then
        assertThat(result).isEqualTo(900);
    }

    @Test
    void 현재_잔액보다_많은_돈을_출금하면_예외가_발생한다() {
        // given
        Account account = new Account(MEMBER, NUMBER);

        // when

        // then
        assertThatThrownBy(() -> account.withdraw(1100))
                .isInstanceOf(InvalidAccountException.class)
                .hasMessage("현재 계좌 잔액보다 많은 돈을 출금할 수 없습니다.");
    }

    @Test
    void 입금한다() {
        // given
        Account account = new Account(MEMBER, NUMBER);

        // when
        int result = account.deposit(100);

        // then
        assertThat(result).isEqualTo(1100);
    }
}
