package com.get.hyphenbackenduser.domain.auth.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.USER_EMAIL_AND_NAME_NOT_BLANK;
import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.USER_PASSWORD_NOT_BLANK;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @NotBlank(message = USER_EMAIL_AND_NAME_NOT_BLANK)
    private String id;

    @NotBlank(message = USER_PASSWORD_NOT_BLANK)
    private String password;
}
