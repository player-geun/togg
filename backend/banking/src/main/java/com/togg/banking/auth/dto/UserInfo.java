package com.togg.banking.auth.dto;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getId();

    public abstract String getName();
}
