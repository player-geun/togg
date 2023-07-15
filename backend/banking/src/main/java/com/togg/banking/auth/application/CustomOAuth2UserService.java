package com.togg.banking.auth.application;

import com.togg.banking.auth.domain.CustomOAuth2User;
import com.togg.banking.auth.dto.OAuthAttributes;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.MemberRepository;
import com.togg.banking.member.domain.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractedAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);
        Member member = getMember(extractedAttributes, socialType);
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes,
                extractedAttributes.getNameAttributeKey(),
                member.getInvestmentType(),
                member.getEmail(),
                member.getRole());
    }

    private SocialType getSocialType(String registrationId) {
        SocialType type = SocialType.from(registrationId);
        if(type.isNaver()) {
            return SocialType.NAVER;
        }
        return SocialType.GOOGLE;
    }

    private Member getMember(OAuthAttributes attributes, SocialType socialType) {
        Member member = memberRepository.findBySocialTypeAndSocialId(socialType,
                attributes.getUserInfo().getId()).orElse(null);

        if(member == null) {
            return saveMember(attributes, socialType);
        }
        return member;
    }

    private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
        Member member = attributes.toMember(socialType, attributes.getUserInfo());
        return memberRepository.save(member);
    }
}
