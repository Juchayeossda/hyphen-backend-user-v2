package com.get.hyphenbackenduser.domain.mail.exception;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MailAuthKeyCreateException extends CustomException {

    public static final CustomException EXCEPTION = new MailAuthKeyCreateException();

    private MailAuthKeyCreateException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "메일 인증키 생성에 실패하였습니다.");
    }
}
