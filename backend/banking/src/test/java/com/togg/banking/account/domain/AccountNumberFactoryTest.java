package com.togg.banking.account.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountNumberFactoryTest {

    @Test
    void 랜덤한_계좌번호를_생성한다() {
        // given & when
        String result = AccountNumberFactory.createNumber();

        // then
        assertThat(result).hasSize(12);
    }
}
