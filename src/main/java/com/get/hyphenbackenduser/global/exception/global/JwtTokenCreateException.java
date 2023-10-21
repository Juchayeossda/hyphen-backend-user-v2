package com.get.hyphenbackenduser.global.exception.global;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class JwtTokenCreateException extends CustomException {

    public static final CustomException EXCEPTION = new JwtTokenCreateException();

    private JwtTokenCreateException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "JWT 토큰 생성에 실패하였습니다.");
    }
}
