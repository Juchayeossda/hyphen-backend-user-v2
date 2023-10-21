package com.get.hyphenbackenduser.domain.token.presentation;

import com.get.hyphenbackenduser.domain.token.presentation.dto.request.RefreshAccessTokenRequest;
import com.get.hyphenbackenduser.global.lib.jwt.JwtProvider;
import com.get.hyphenbackenduser.global.lib.security.service.SecurityService;
import com.get.hyphenbackenduser.global.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_OK;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final SecurityService securityService;
    private final JwtProvider jwtProvider;

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Long> validate() {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, securityService.getAuthUserInfo().getId());
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> refresh(@Valid @RequestBody RefreshAccessTokenRequest request) {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, jwtProvider.refresh(request.getRefreshToken()));
    }
}
