package com.togg.banking.common.fixtures;

import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.member.domain.InvestmentType;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.Role;
import com.togg.banking.member.domain.SocialType;
import com.togg.banking.member.dto.MemberResponse;

public class MemberFixtures {

    public static final SignUpRequest SIGN_UP_REQUEST = new SignUpRequest(InvestmentType.SAFE);
    public static final SignUpResponse SIGN_UP_RESPONSE = new SignUpResponse(1L, "geunwoo.dev@gamil.com", "이근우", InvestmentType.SAFE);
    public static final MemberResponse MEMBER_RESPONSE = new MemberResponse(member());

    public static Member member() {
        return new Member("이근우", "geunwoo.dev@gmail.com", Role.MEMBER, SocialType.KAKAO, "1");
    }

    public static Member receiver() {
        return new Member("이준우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "2");
    }
}
