package com.get.hyphenbackenduser.global.lib.oauth2.attributes;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.enums.SocialType;
import com.get.hyphenbackenduser.domain.user.enums.UserRole;
import com.get.hyphenbackenduser.global.lib.oauth2.user.global.OAuth2User;
import com.get.hyphenbackenduser.global.lib.oauth2.user.global.social.GoogleOAuth2User;
import com.get.hyphenbackenduser.global.lib.oauth2.user.global.social.KakaoOAuth2User;
import com.get.hyphenbackenduser.global.lib.oauth2.user.global.social.NaverOAuth2User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2User oauth2User;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2User oauth2User) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2User = oauth2User;
    }

    public static OAuthAttributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {
        if (socialType == SocialType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (socialType == SocialType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2User(new KakaoOAuth2User(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2User(new GoogleOAuth2User(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2User(new NaverOAuth2User(attributes))
                .build();
    }

    public User toEntity(SocialType socialType, OAuth2User oauth2User) {
        return User.builder()
                .email(oauth2User.getEmail())
                .socialId(oauth2User.getId())
                .socialType(socialType)
                .userRole(UserRole.GUEST)
                .build();
    }
}
