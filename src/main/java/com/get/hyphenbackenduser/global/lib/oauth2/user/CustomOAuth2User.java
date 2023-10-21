package com.get.hyphenbackenduser.global.lib.oauth2.user;

import com.get.hyphenbackenduser.domain.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;


@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private String socialId;
    private UserRole role;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String socialId, UserRole role) {
        super(authorities, attributes, nameAttributeKey);
        this.socialId = socialId;
        this.role = role;
    }
}
