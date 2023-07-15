package com.togg.banking.auth.dto;

import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.Role;
import com.togg.banking.member.domain.SocialType;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private UserInfo userInfo;

    private OAuthAttributes(String nameAttributeKey, UserInfo userInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.userInfo = userInfo;
    }

    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if (socialType.isNaver()) {
            return new OAuthAttributes(userNameAttributeName, new NaverUserInfo(attributes));
        }
        return new OAuthAttributes(userNameAttributeName, new GoogleUserInfo(attributes));
    }

    public Member toMember(SocialType socialType, UserInfo userInfo) {
        return new Member(userInfo.getName(), UUID.randomUUID() + "@togg.com",
                Role.GUEST, socialType, userInfo.getId());
    }
}
