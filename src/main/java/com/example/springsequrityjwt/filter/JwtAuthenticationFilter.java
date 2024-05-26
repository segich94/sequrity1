package com.example.springsequrityjwt.filter;

import com.example.springsequrityjwt.JwtAuthenticationToken;
import com.example.springsequrityjwt.service.JwtService;
import com.example.springsequrityjwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RequiredArgsConstructor
@Component

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractTokenFromRequest(request);

        if (token != null && validateToken(token)) {
            Authentication authentication = createAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Логика извлечения токена из запроса (например, из заголовка Authorization)
        String authHeader = request.getHeader("Authorization");
        if (!authHeader.startsWith("Bearer ")){
            return null;
        }
        else return authHeader;
    }

    private boolean validateToken(String token) {
        // Логика верификации токена
        String jwt = token.substring("Bearer ".length());
        String username = JwtUtil.getUsernameFromToken(jwt);
        return !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null;
    }


    private Authentication createAuthentication(String token) {
        // Получение информации о пользователе из токена
        UserDetails userDetails = extractUserDetailsFromToken(token);

        // Создание объекта Authentication
        return new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    private UserDetails extractUserDetailsFromToken(String token) {
            // Логика извлечения информации о пользователе из токена
        return jwtService.getUserByUsername(JwtUtil.getUsernameFromToken(token));
    }

}

