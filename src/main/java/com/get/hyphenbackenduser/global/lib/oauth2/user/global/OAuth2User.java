package com.get.hyphenbackenduser.global.lib.oauth2.user.global;

import java.util.Map;

public abstract class OAuth2User {

    protected Map<String, Object> attributes;
    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
    public OAuth2User(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}