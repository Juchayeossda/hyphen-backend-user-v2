package com.get.hyphenbackenduser.domain.token.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.get.hyphenbackenduser.global.statics.ValidMessageConstants.REFRESH_TOKEN_NOT_BLANK;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshAccessTokenRequest {

    @NotBlank(message = REFRESH_TOKEN_NOT_BLANK)
    private String refreshToken;
}
