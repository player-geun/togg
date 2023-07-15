package com.togg.banking.auth.dto;

import com.togg.banking.member.domain.InvestmentType;
import com.togg.banking.member.domain.Member;

public record SignUpResponse(Long id, String email, String name, InvestmentType investmentType) {

    public SignUpResponse(Member member) {
        this(member.getId(), member.getEmail(), member.getName(), member.getInvestmentType());
    }
}
