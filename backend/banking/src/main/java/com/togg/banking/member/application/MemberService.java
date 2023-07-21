package com.togg.banking.member.application;

import com.togg.banking.auth.dto.SignUpRequest;
import com.togg.banking.auth.dto.SignUpResponse;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.MemberRepository;
import com.togg.banking.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse findByRefreshToken(String refreshToken) {
        Member member = memberRepository.getByRefreshToken(refreshToken);
        return new MemberResponse(member);
    }

    @Transactional
    public SignUpResponse signUp(Long id, SignUpRequest request) {
        Member member = memberRepository.getById(id);
        member.authorize();
        member.changeInvestType(request.investmentType());
        return new SignUpResponse(member);
    }

    public MemberResponse findById(Long id) {
        Member member = memberRepository.getById(id);
        return new MemberResponse(member);
    }

    public Member findByIdForOtherTransaction(Long id) {
        return memberRepository.getById(id);
    }

    @Transactional
    public void updateByRefreshToken(String refreshToken, String updatingRefreshToken) {
        Member member = memberRepository.getByRefreshToken(refreshToken);
        member.changeRefreshToken(updatingRefreshToken);
    }

    @Transactional
    public void updateRefreshTokenById(Long id, String updatingRefreshToken) {
        Member member = memberRepository.getById(id);
        member.changeRefreshToken(updatingRefreshToken);
    }
}
