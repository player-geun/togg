package com.togg.banking.member.domain;

import com.togg.banking.member.exception.InvalidMemberException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthdate;

    @Enumerated(EnumType.STRING)
    private InvestmentType investmentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private String socialId;

    private String refreshToken;

    public Member(String name, String birthdate, SocialType socialType, String socialId) {
        this(name, birthdate, null, socialType, socialId);
    }

    public Member(String name, String birthdate, InvestmentType investmentType, SocialType socialType, String socialId) {
        validateBirthdate(birthdate);
        this.name = name;
        this.birthdate = birthdate;
        this.investmentType = investmentType;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    private void validateBirthdate(String birthdate) {
        if (!birthdate.matches("^(19|20)\\d\\d[-](0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])$")) {
            throw new InvalidMemberException("생년월일 형식(YYYY-MM-DD)이 올바르지 않습니다.");
        }
    }

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
