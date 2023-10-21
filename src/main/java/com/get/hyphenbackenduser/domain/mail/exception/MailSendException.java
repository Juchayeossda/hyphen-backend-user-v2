package com.get.hyphenbackenduser.domain.mail.exception;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class MailSendException extends CustomException {

    public static final CustomException EXCEPTION = new MailSendException();

    private MailSendException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "메일을 보내는데 실패하였습니다.");
    }
}
