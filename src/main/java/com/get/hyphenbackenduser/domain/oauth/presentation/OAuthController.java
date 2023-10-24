package com.get.hyphenbackenduser.domain.oauth.presentation;

import com.get.hyphenbackenduser.domain.oauth.service.OAuthService;
import com.get.hyphenbackenduser.domain.user.enums.SocialType;
import com.get.hyphenbackenduser.global.lib.jwt.response.TokenResponse;
import com.get.hyphenbackenduser.global.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.get.hyphenbackenduser.global.statics.response.ResponseMessageConstants.SUCCESSFUL_OK;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @PostMapping("/google")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public ResponseEntity<?> googleAuth() {
        return new ResponseEntity<>(oAuthService.oAuthLogin(SocialType.GOOGLE), HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/naver")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public ResponseEntity<?> naverAuth() {
        return new ResponseEntity<>(oAuthService.oAuthLogin(SocialType.NAVER), HttpStatus.MOVED_PERMANENTLY);
    }

    @PostMapping("/kakao")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public ResponseEntity<?> kakaoAuth() {
        return new ResponseEntity<>(oAuthService.oAuthLogin(SocialType.KAKAO), HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/social/success")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<TokenResponse> googleOAuthLoginSuccess(Authentication authentication) {
        return ResponseData.of(HttpStatus.OK.value(), SUCCESSFUL_OK, oAuthService.socialOAuthSuccess(authentication));
    }
}
