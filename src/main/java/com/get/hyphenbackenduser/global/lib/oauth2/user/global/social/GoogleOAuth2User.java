package com.get.hyphenbackenduser.global.lib.oauth2.user.global.social;

import com.get.hyphenbackenduser.global.lib.oauth2.user.global.OAuth2User;

import java.util.Map;

public class GoogleOAuth2User extends OAuth2User {

    public GoogleOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}

