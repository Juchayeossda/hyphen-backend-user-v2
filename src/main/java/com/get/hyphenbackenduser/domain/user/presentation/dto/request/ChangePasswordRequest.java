package com.get.hyphenbackenduser.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangePasswordRequest {

    @NotBlank(message = PREVIOUS_PASSWORD_NOT_BLANK)
    @Size(min=8, max=24, message = USER_PASSWORD_SIZE)
    private String previousPassword;

    @NotBlank(message = NEW_PASSWORD_NOT_BLANK)
    @Size(min=8, max=24, message = USER_PASSWORD_SIZE)
    private String newPassword;
}
