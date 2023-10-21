package com.get.hyphenbackenduser.domain.user.exception;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends CustomException {

    public static final CustomException EXCEPTION = new PasswordNotMatchException();

    private PasswordNotMatchException() {
        super(HttpStatus.BAD_REQUEST, "비밀번호가 옳지 않습니다.");
    }
}