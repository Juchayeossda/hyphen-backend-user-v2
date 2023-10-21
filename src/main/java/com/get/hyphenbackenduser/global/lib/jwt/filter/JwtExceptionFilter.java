package com.get.hyphenbackenduser.global.lib.jwt.filter;

import com.get.hyphenbackenduser.global.exception.global.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException | ExpiredJwtException unauthorizedException) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response);
        } catch (Exception e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response) {
        response.setStatus(status.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
    }
}