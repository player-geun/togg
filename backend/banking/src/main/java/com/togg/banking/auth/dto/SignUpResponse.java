package com.togg.banking.auth.dto;

import com.togg.banking.member.domain.InvestmentType;
import com.togg.banking.member.domain.Member;

public record SignUpResponse(String email, String name, InvestmentType investmentType) {

    public SignUpResponse(Member member) {
        this(member.getEmail(), member.getName(), member.getInvestmentType());
    }
}
