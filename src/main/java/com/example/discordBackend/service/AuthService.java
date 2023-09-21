package com.example.discordBackend.service;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.auth.*;
import org.springframework.security.core.Authentication;

public interface AuthService {
    ApiResponse login(LoginReqDto loginDto);
    ApiResponse register(RegisterReqDto registerDto);
    ApiResponse getUser(Authentication authentication);
}
