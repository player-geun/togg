package com.togg.banking.auth.dto;

import com.togg.banking.member.domain.InvestmentType;

public record SignUpRequest(InvestmentType investmentType) {
}
