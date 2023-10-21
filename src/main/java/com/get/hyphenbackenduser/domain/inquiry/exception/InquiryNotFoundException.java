package com.get.hyphenbackenduser.domain.inquiry.exception;

import com.get.hyphenbackenduser.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InquiryNotFoundException extends CustomException {


    public static final CustomException EXCEPTION = new InquiryNotFoundException();

    private InquiryNotFoundException() {
        super(HttpStatus.NOT_FOUND, "문의 정보를 찾지 못했습니다.");
    }
}
