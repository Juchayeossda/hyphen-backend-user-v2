package com.get.hyphenbackenduser.global.lib.oauth2.service;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.domain.repository.UserRepository;
import com.get.hyphenbackenduser.domain.user.enums.SocialType;
import com.get.hyphenbackenduser.global.lib.oauth2.attributes.OAuthAttributes;
import com.get.hyphenbackenduser.global.lib.oauth2.user.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        SocialType socialType = getSocialType(userRequest.getClientRegistration().getRegistrationId());
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = socialType == SocialType.NAVER ? (Map<String, Object>) oAuth2User.getAttributes().get(userNameAttributeName) : oAuth2User.getAttributes();
        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, socialType == SocialType.NAVER ? "id" : userNameAttributeName, attributes);
        User createdUser = getUser(extractAttributes, socialType);
        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getUserRole().getKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getSocialId(),
                createdUser.getUserRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return SocialType.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return SocialType.KAKAO;
        }
        return SocialType.GOOGLE;
    }

    private User getUser(OAuthAttributes attributes, SocialType socialType) {
        User findUser = userRepository.findBySocialIdAndSocialType(attributes.getOauth2User().getId(), socialType).orElse(null);
        if(findUser == null) {
            return saveUser(attributes, socialType);
        }
        return findUser;
    }

    private User saveUser(OAuthAttributes attributes, SocialType socialType) {
        User createdUser = attributes.toEntity(socialType, attributes.getOauth2User());
        User user = userRepository.save(createdUser);
        user.updateUser(String.format("Guest%s", user.getId().toString()), null, null);
        return userRepository.save(user);
    }
}
