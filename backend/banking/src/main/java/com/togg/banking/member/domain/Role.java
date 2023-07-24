package com.togg.banking.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    GUEST("ROLE_GUEST"), MEMBER("ROLE_MEMBER");

    private final String key;

    public boolean isGuest() {
        return this == GUEST;
    }
}
