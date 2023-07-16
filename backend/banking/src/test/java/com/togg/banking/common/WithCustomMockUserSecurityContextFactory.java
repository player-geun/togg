package com.togg.banking.common;

import com.togg.banking.auth.domain.AuthenticationToken;
import com.togg.banking.auth.dto.LoginMember;
import com.togg.banking.member.domain.Member;
import com.togg.banking.member.domain.Role;
import com.togg.banking.member.domain.SocialType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        Member member = new Member("이근우", "g@gmail.com", Role.MEMBER, SocialType.KAKAO, "1");
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(member.getRole().name());
        AuthenticationToken authentication = new AuthenticationToken(
                authorities,
                new LoginMember(member.getId(), member.getName(), member.getEmail()),
                null);

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        return context;
    }
}
