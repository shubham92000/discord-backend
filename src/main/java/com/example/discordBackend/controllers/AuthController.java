package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.auth.*;
import com.example.discordBackend.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private Logger log = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginReqDto loginDto){
        log.info("login req: "+loginDto);
        ApiResponse response = authService.login(loginDto);
        log.info("login res: "+response);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterReqDto registerDto){
        log.info("register req: "+registerDto);
        ApiResponse response = authService.register(registerDto);
        log.info("register res: "+response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getUser(Authentication authentication) {
        ApiResponse response = authService.getUser(authentication);
        return ResponseEntity.ok(response);
    }
}
