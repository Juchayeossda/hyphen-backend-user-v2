package com.get.hyphenbackenduser.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.NEW_NAME_NOT_BLANK;
import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.USER_NAME_SIZE;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RenameRequest {

    @NotBlank(message = NEW_NAME_NOT_BLANK)
    @Size(min=2, max=12, message = USER_NAME_SIZE)
    private String newName;
}
