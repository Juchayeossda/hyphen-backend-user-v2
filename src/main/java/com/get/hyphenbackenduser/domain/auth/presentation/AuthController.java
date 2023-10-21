package com.get.hyphenbackenduser.domain.auth.presentation;

import com.get.hyphenbackenduser.domain.auth.presentation.dto.request.LoginRequest;
import com.get.hyphenbackenduser.domain.auth.presentation.dto.request.CreateUserRequest;
import com.get.hyphenbackenduser.global.lib.jwt.response.TokenResponse;
import com.get.hyphenbackenduser.domain.auth.service.AuthService;
import com.get.hyphenbackenduser.global.response.ResponseData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_CREATED;
import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_OK;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<Object> join(@Valid @RequestBody CreateUserRequest request) {
        authService.join(request);
        return ResponseData.of(HttpStatus.CREATED.value(), SUCCESSFUL_CREATED);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<TokenResponse> auth(@Valid @RequestBody LoginRequest request) {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, authService.authenticate(request));
    }

    @PostMapping("/unregister")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<Object> unregister() {
        authService.unregister();
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK);
    }
}
