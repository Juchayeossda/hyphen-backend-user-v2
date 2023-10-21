package com.get.hyphenbackenduser.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {

    ACTIVE("ACTIVE"), DEACTIVATED("DEACTIVATED"), BANNED("BANNED");
    private final String key;
}
