package com.togg.banking.member.domain;

public enum SocialType {
    KAKAO, NAVER, GOOGLE;

    public static SocialType from(String value) {
        return SocialType.valueOf(value.toUpperCase());
    }

    public boolean isNaver() {
        return this == NAVER;
    }
}
