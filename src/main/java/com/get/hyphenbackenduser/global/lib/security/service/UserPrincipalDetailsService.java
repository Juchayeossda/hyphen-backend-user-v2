package com.get.hyphenbackenduser.global.lib.security.service;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.domain.repository.UserRepository;
import com.get.hyphenbackenduser.domain.user.exception.UserNotFoundException;
import com.get.hyphenbackenduser.global.lib.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(name)).orElseThrow(() -> UserNotFoundException.NAME_EXCEPTION);
        return UserPrincipal.create(user);
    }
}
