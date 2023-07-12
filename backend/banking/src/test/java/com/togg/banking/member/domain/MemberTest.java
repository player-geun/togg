package com.togg.banking.member.domain;

import com.togg.banking.member.exception.InvalidMemberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @ParameterizedTest
    @ValueSource(strings = {"202-01-01", "20230101", "2023-0101"})
    void 생년월일_형식이_올바르지_않으면_예외가_발생한다(String birthdate) {
        // given & when & then
        assertThatThrownBy(() -> new Member("이근우", birthdate, SocialType.KAKAO, "1"))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("생년월일 형식(YYYY-MM-DD)이 올바르지 않습니다.");
    }
}
