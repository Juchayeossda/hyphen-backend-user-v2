package com.get.hyphenbackenduser.global.lib.oauth2.handler;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.domain.repository.UserRepository;
import com.get.hyphenbackenduser.domain.user.enums.UserRole;
import com.get.hyphenbackenduser.domain.user.exception.UserNotFoundException;
import com.get.hyphenbackenduser.global.lib.oauth2.user.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = userRepository.findBySocialId(((CustomOAuth2User) authentication.getPrincipal()).getSocialId()).orElseThrow(() -> UserNotFoundException.SOCIAL_EXCEPTION);
        if (user.getUserRole() == UserRole.GUEST) {
            user.setSocial();
            userRepository.save(user);
        }
        setDefaultTargetUrl("/api/auth/social/success");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}