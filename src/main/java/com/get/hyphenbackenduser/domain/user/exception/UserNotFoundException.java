package com.get.hyphenbackenduser.domain.user.exception;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {


    public static final CustomException EXCEPTION = new UserNotFoundException("아이디를");
    public static final CustomException EMAIL_EXCEPTION = new UserNotFoundException("이메일을");
    public static final CustomException NAME_EXCEPTION = new UserNotFoundException("이름을");
    public static final CustomException SOCIAL_EXCEPTION = new UserNotFoundException();

    private UserNotFoundException(String identifierName) {
        super(HttpStatus.NOT_FOUND, String.format("해당 %s 가진 유저를 찾지 못했습니다.", identifierName));
    }

    private UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 소셜 유저를 찾지 못했습니다.");
    }
}
