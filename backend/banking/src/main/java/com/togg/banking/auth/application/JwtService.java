package com.togg.banking.auth.application;

import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtService {

    private final MemberRepository memberRepository;

    @Transactional
    public void updateByRefreshToken(String refreshToken, String updatedRefreshToken) {
        Member member = memberRepository.getByRefreshToken(refreshToken);
        member.changeRefreshToken(updatedRefreshToken);
        memberRepository.save(member);
    }
}
