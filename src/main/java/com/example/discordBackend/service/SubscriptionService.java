package com.example.discordBackend.service;

import com.example.discordBackend.dtos.ApiResponse;
import org.springframework.security.core.Authentication;

public interface SubscriptionService {
    ApiResponse subscribeComplete(Authentication authentication);
}
