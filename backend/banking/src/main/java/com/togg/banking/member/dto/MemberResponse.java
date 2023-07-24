package com.togg.banking.member.dto;

import com.togg.banking.member.domain.InvestmentType;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.Role;

public record MemberResponse(Long id, String name, Role role, InvestmentType investmentType) {

    public MemberResponse(Member member) {
        this(member.getId(), member.getName(), member.getRole(), member.getInvestmentType());
    }
}
