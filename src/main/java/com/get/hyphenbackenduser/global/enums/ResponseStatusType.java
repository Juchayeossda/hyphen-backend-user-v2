package com.get.hyphenbackenduser.global.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseStatusType {

    SUCCESS("[SUCCESS]"), FAILURE("[FAILURE]");
    private final String value;
}