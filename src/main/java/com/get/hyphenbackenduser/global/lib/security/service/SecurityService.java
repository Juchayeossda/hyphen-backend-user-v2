package com.get.hyphenbackenduser.global.lib.security.service;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.global.lib.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {


    public User getAuthUserInfo() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUser();
    }
}
