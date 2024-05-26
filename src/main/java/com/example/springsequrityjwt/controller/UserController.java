package com.example.springsequrityjwt.controller;

import com.example.springsequrityjwt.dto.AuthRequestDto;
import com.example.springsequrityjwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> createToken(@RequestBody AuthRequestDto authRequestDto){
        return jwtService.createJwtToken(authRequestDto);
    }
}
