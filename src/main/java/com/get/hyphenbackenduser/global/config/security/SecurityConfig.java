package com.get.hyphenbackenduser.global.config.security;

import com.get.hyphenbackenduser.global.lib.jwt.filter.JwtAuthenticationFilter;
import com.get.hyphenbackenduser.global.lib.jwt.filter.JwtExceptionFilter;
import com.get.hyphenbackenduser.global.lib.oauth2.handler.OAuth2LoginFailureHandler;
import com.get.hyphenbackenduser.global.lib.oauth2.handler.OAuth2LoginSuccessHandler;
import com.get.hyphenbackenduser.global.lib.oauth2.service.CustomOAuth2UserService;
import com.get.hyphenbackenduser.global.lib.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final AuthenticationProvider authenticationProvider;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(handlingConfigures -> handlingConfigures.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(authorize -> authorize
//                                .requestMatchers(HttpMethod.POST, "/v2/api/auth/unregister").authenticated()
//                                .requestMatchers("/", "/v2", "/v2/api/auth/**", "/v2/api/token/**", "/v2/api/mail/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/unregister").authenticated()
                        .requestMatchers("/", "/api/auth/**", "/api/token/**", "/api/mail/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
}