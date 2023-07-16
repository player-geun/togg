package com.togg.banking.member.dto;

import com.togg.banking.member.domain.InvestmentType;
import com.togg.banking.member.domain.Member;

public record MemberResponse(Long id, String email, String name, InvestmentType investmentType) {

    public MemberResponse(Member member) {
        this(member.getId(), member.getEmail(), member.getName(), member.getInvestmentType());
    }
}
