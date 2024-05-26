package com.example.springsequrityjwt.service;

import com.example.springsequrityjwt.JwtAuthenticationProvider;
import com.example.springsequrityjwt.JwtAuthenticationToken;
import com.example.springsequrityjwt.dto.AuthRequestDto;
import com.example.springsequrityjwt.dto.AuthResponseDto;
import com.example.springsequrityjwt.model.User;
import com.example.springsequrityjwt.model.UserRepository;
import com.example.springsequrityjwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtService jwtService;

    public ResponseEntity<?> createJwtToken(AuthRequestDto authRequestDto){
        authenticationManager.authenticate(jwtAuthenticationProvider
                .authenticate(new JwtAuthenticationToken(authRequestDto.getUsername(),authRequestDto.getPassword())));
        String token = JwtUtil.generateToken(jwtService.getUserByUsername(authRequestDto.getUsername()));
        return ResponseEntity.status(201).body(new AuthResponseDto(token));
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
}
