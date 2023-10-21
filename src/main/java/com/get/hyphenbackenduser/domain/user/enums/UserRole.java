package com.get.hyphenbackenduser.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    GUEST("GUEST"), MEMBER("MEMBER"), MANAGER("MANGER"), ADMIN("ADMIN");
    private final String key;
}