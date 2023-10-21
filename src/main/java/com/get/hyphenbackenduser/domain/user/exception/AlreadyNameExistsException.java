package com.get.hyphenbackenduser.domain.user.exception;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AlreadyNameExistsException extends CustomException {

    public static final CustomException EXCEPTION = new AlreadyNameExistsException();

    private AlreadyNameExistsException() {
        super(HttpStatus.FORBIDDEN, "이미 존재하는 이름입니다.");
    }

}
