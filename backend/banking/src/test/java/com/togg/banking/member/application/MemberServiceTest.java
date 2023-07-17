package com.togg.banking.member.application;

import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.member.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    void 회원가입을_한다() {
        // given
        SignUpRequest request = new SignUpRequest(InvestmentType.SAFE);
        Member member = new Member("이근우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "1");
        given(memberRepository.getById(any())).willReturn(member);

        // when
        SignUpResponse result = memberService.signUp(1L, request);

        // then
        assertThat(result.investmentType()).isEqualTo(InvestmentType.SAFE);
    }
}
