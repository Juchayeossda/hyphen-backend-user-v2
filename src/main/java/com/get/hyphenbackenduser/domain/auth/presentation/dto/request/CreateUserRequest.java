package com.get.hyphenbackenduser.domain.auth.presentation.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserRequest {

    @NotBlank(message = USER_EMAIL_NOT_BLANK)
    @Email(message = EMAIL_NOT_TYPE)
    private String email;

    @NotBlank(message = USER_PASSWORD_NOT_BLANK)
    @Size(min=8, max=24, message = USER_PASSWORD_SIZE)
    private String password;
}
