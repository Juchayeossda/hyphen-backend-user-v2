package com.get.hyphenbackenduser.domain.oauth.service;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.domain.repository.UserRepository;
import com.get.hyphenbackenduser.domain.user.enums.SocialType;
import com.get.hyphenbackenduser.domain.user.exception.UserNotFoundException;
import com.get.hyphenbackenduser.global.annotation.ServiceWithTransactionalReadOnly;
import com.get.hyphenbackenduser.global.lib.jwt.JwtProvider;
import com.get.hyphenbackenduser.global.lib.jwt.JwtType;
import com.get.hyphenbackenduser.global.lib.jwt.response.TokenResponse;
import com.get.hyphenbackenduser.global.lib.oauth2.user.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class OAuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public HttpHeaders oAuthLogin(SocialType socialType) {
        HttpHeaders headers = new HttpHeaders();
        switch (socialType) {
            case GOOGLE -> headers.setLocation(URI.create("/oauth2/authorization/google"));
            case NAVER -> headers.setLocation(URI.create("/oauth2/authorization/naver"));
            case KAKAO -> headers.setLocation(URI.create("/oauth2/authorization/kakao"));
        }
        return headers;
    }

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse socialOAuthSuccess(Authentication authentication) {
        User user = userRepository.findBySocialId(((CustomOAuth2User) authentication.getPrincipal()).getSocialId()).orElseThrow(() -> UserNotFoundException.SOCIAL_EXCEPTION);
        return new TokenResponse(jwtProvider.createToken(user, JwtType.ACCESS), jwtProvider.createToken(user, JwtType.REFRESH));
    }
}
