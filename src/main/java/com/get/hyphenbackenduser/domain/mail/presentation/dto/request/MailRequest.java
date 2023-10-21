package com.get.hyphenbackenduser.domain.mail.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.EMAIL_NOT_BLANK;
import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.EMAIL_NOT_TYPE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MailRequest {

    @NotBlank(message = EMAIL_NOT_BLANK)
    @Email(message = EMAIL_NOT_TYPE)
    private String mail;
}
