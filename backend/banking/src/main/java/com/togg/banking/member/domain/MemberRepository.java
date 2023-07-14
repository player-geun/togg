package com.togg.banking.member.domain;

import com.togg.banking.common.exception.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    default Member getByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new NoSuchEntityException("해당 이메일의 회원이 존재하지 않습니다."));
    }

    Optional<Member> findByRefreshToken(String refreshToken);

    default Member getByRefreshToken(String refreshToken) {
        return findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NoSuchEntityException("해당 리프레시 토큰의 회원이 존재하지 않습니다."));
    }

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    default Member getBySocialTypeAndSocialId(SocialType socialType, String socialId) {
        return findBySocialTypeAndSocialId(socialType, socialId)
                .orElseThrow(() -> new NoSuchEntityException("해당 소셜타입과 소셜 아이디의 회원이 존재하지 않습니다."));
    }
}
