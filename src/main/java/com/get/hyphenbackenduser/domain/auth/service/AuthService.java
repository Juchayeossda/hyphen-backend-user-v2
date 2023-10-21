package com.get.hyphenbackenduser.domain.auth.service;

import com.get.hyphenbackenduser.domain.auth.exception.UserEmailExistsException;
import com.get.hyphenbackenduser.domain.auth.presentation.dto.request.CreateUserRequest;
import com.get.hyphenbackenduser.domain.auth.presentation.dto.request.LoginRequest;
import com.get.hyphenbackenduser.domain.user.enums.UserRole;
import com.get.hyphenbackenduser.global.lib.jwt.response.TokenResponse;
import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.domain.repository.UserRepository;
import com.get.hyphenbackenduser.domain.user.enums.UserStatus;
import com.get.hyphenbackenduser.domain.user.exception.PasswordNotMatchException;
import com.get.hyphenbackenduser.domain.user.exception.UserDeactivatedException;
import com.get.hyphenbackenduser.domain.user.exception.UserNotFoundException;
import com.get.hyphenbackenduser.global.annotation.ServiceWithTransactionalReadOnly;
import com.get.hyphenbackenduser.global.lib.jwt.JwtProvider;
import com.get.hyphenbackenduser.global.lib.jwt.JwtType;
import com.get.hyphenbackenduser.global.lib.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class AuthService {

    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public void join(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw UserEmailExistsException.EXCEPTION;
        }
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .build();
        userRepository.save(user);
        user.updateUser(String.format("Guest%s", user.getId().toString()), null, null);
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse authenticate(LoginRequest request) {
        User user;
        if (EmailValidator.getInstance().isValid(request.getId())) {
            user = userRepository.findByEmail(request.getId()).orElseThrow(() -> UserNotFoundException.EMAIL_EXCEPTION);
        } else {
            user = userRepository.findByName(request.getId()).orElseThrow(() -> UserNotFoundException.NAME_EXCEPTION);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw PasswordNotMatchException.EXCEPTION;
        }
        if (!user.getUserStatus().equals(UserStatus.ACTIVE)) {
            throw UserDeactivatedException.EXCEPTION;
        }
        String accessToken = jwtProvider.createToken(user, JwtType.ACCESS);
        String refreshToken = jwtProvider.createToken(user, JwtType.REFRESH);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional(rollbackFor = Exception.class)
    public void unregister() {
        User user = securityService.getAuthUserInfo();
        user.deactivateUser();
        userRepository.save(user);
    }
}
