package com.get.hyphenbackenduser.global.lib.oauth2.user.global.social;

import com.get.hyphenbackenduser.global.lib.oauth2.user.global.OAuth2User;

import java.util.Map;

public class KakaoOAuth2User extends OAuth2User {

    public KakaoOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        if (account == null || profile == null) {
            return null;
        }
        return (String) profile.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        if (account == null) {
            return null;
        }
        return (String) account.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        if (account == null || profile == null) {
            return null;
        }
        return (String) profile.get("profile_image_url");
    }
}

