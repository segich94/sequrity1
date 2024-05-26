package com.example.springsequrityjwt;

import com.example.springsequrityjwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private JwtService jwtService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Логика аутентификации с использованием токена
        UserDetails userDetails = jwtService.getUserByUsername(authentication.getName());
        if (userDetails.getPassword().equals(authentication.getCredentials())){
            return new JwtAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
        }else
            throw new BadCredentialsException("Bad cred");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}