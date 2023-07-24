package com.togg.banking.auth.domain;

import com.togg.banking.member.domain.InvestmentType;
import com.togg.banking.member.domain.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private InvestmentType investmentType;
    private Long id;
    private Role role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes,
                            String nameAttributeKey,
                            InvestmentType investmentType,
                            Long id,
                            Role role) {
        super(authorities, attributes, nameAttributeKey);
        this.investmentType = investmentType;
        this.id = id;
        this.role = role;
    }

    public boolean isGuest() {
        return role.isGuest();
    }
}
