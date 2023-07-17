package com.togg.banking.member.application;

import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.common.ServiceTest;
import com.togg.banking.common.fixtures.MemberFixtures;
import com.togg.banking.member.domain.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class MemberServiceTest extends ServiceTest {

    @Test
    void 회원가입을_한다() {
        // given
        SignUpRequest request = MemberFixtures.SIGN_UP_REQUEST;
        Member member = MemberFixtures.member();
        given(memberRepository.getById(any())).willReturn(member);

        // when
        SignUpResponse result = memberService.signUp(1L, request);

        // then
        assertThat(result.investmentType()).isEqualTo(request.investmentType());
    }
}
