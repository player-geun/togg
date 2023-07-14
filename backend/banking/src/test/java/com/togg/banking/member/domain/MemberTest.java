package com.togg.banking.member.domain;

import com.togg.banking.member.exception.InvalidMemberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @ParameterizedTest
    @ValueSource(strings = {"geun@", "geun@gmail", "geun@gmail.c"})
    void 이메일_형식이_올바르지_않으면_예외가_발생한다(String email) {
        // given & when & then
        assertThatThrownBy(() -> new Member("이근우", email, SocialType.KAKAO, "1"))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }
}
