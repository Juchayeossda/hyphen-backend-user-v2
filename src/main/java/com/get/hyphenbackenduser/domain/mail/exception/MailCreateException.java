package com.get.hyphenbackenduser.domain.mail.exception;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MailCreateException extends CustomException {

    public static final CustomException EXCEPTION = new MailCreateException();

    private MailCreateException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "메일을 생성할 수 없습니다.");
    }
}
