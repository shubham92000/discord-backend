package com.example.discordBackend.service;

import com.example.discordBackend.dtos.auth.*;
import org.springframework.security.core.Authentication;

public interface AuthService {
    LoginResDto login(LoginReqDto loginDto);
    RegisterResDto register(RegisterReqDto registerDto);
    UserResDto getUser(Authentication authentication);
}
