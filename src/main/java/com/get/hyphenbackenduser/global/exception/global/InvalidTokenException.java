package com.get.hyphenbackenduser.global.exception.global;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {

    public static final CustomException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "토큰 값이 유효하지 않습니다.");
    }
}
