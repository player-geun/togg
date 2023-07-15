package com.togg.banking.member.application;

import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByEmail(String email) {
        return memberRepository.getByEmail(email);
    }

    public Member findByRefreshToken(String refreshToken) {
        return memberRepository.getByRefreshToken(refreshToken);
    }

    @Transactional
    public SignUpResponse signUp(String email, SignUpRequest request) {
        Member member = memberRepository.getByEmail(email);
        member.authorize();
        member.changeInvestType(request.investmentType());
        return new SignUpResponse(member);
    }

    @Transactional
    public void updateByRefreshToken(String refreshToken, String updatingRefreshToken) {
        Member member = memberRepository.getByRefreshToken(refreshToken);
        member.changeRefreshToken(updatingRefreshToken);
    }

    @Transactional
    public void updateRefreshTokenByEmail(String email, String updatingRefreshToken) {
        Member member = memberRepository.getByEmail(email);
        member.changeRefreshToken(updatingRefreshToken);
    }
}
