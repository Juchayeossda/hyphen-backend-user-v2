package com.get.hyphenbackenduser.global.lib.jwt;

import com.get.hyphenbackenduser.domain.user.domain.User;
import com.get.hyphenbackenduser.domain.user.domain.repository.UserRepository;
import com.get.hyphenbackenduser.global.exception.global.CredentialsNotFoundException;
import com.get.hyphenbackenduser.global.exception.global.InvalidTokenException;
import com.get.hyphenbackenduser.global.exception.global.JwtTokenCreateException;
import com.get.hyphenbackenduser.global.lib.security.service.UserPrincipalDetailsService;
import com.get.hyphenbackenduser.global.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;
    private final UserPrincipalDetailsService userPrincipalDetailsService;

    public String createToken(User user, JwtType jwtType) {
        String secretKey;
        Calendar expiredDate = Calendar.getInstance();
        expiredDate.setTime(new Date());
        switch (jwtType) {
            case ACCESS -> {
                expiredDate.add(Calendar.DATE, jwtProperties.getExpiration());
                secretKey = jwtProperties.getAccessKey();
            }
            case REFRESH -> {
                expiredDate.add(Calendar.DATE, jwtProperties.getRefreshExpiration());
                secretKey = jwtProperties.getRefreshKey();
            }
            default -> throw JwtTokenCreateException.EXCEPTION;
        }

        byte[] apiKeySecretBytes = Decoders.BASE64.decode(secretKey);
        Key signingKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", SignatureAlgorithm.HS256);

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", String.valueOf(user.getId()));

        JwtBuilder builder = Jwts.builder()
                .setHeaderParams(headerMap)
                .setClaims(claimsMap)
                .setExpiration(expiredDate.getTime())
                .signWith(SignatureAlgorithm.HS256, signingKey);

        return builder.compact();
    }

    public String refresh(String refreshToken) {
        Claims claims = Jwts.parser().setSigningKey(Decoders.BASE64.decode(jwtProperties.getRefreshKey())).parseClaimsJws(refreshToken).getBody();
        User user = userRepository.findById(claims.get("id", Long.class)).orElseThrow(() -> InvalidTokenException.EXCEPTION);
        return createToken(user, JwtType.ACCESS);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        if (token == null) {
            throw CredentialsNotFoundException.EXCEPTION;
        }
        String jwt = token.substring(7);
        UserDetails userDetails = getUserDetails(jwt);
        if (extractClaim(jwt, Claims::getExpiration).before(new Date())) {
            throw InvalidTokenException.EXCEPTION;
        }
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private UserDetails getUserDetails(String token) {
        final Claims claims = extractAllClaims(token);
        String username = (String) claims.get("id");
        return userPrincipalDetailsService.loadUserByUsername(username);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        String secretKey = jwtProperties.getAccessKey();
        byte[] apiKeySecretBytes = Decoders.BASE64.decode(secretKey);
        Key signingKey = Keys.hmacShaKeyFor(apiKeySecretBytes);
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
    }
}
