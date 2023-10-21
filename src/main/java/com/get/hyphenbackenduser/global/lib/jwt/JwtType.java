package com.get.hyphenbackenduser.global.lib.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JwtType {

    ACCESS("ACCESS"), REFRESH("REFRESH");
    private final String key;
}
