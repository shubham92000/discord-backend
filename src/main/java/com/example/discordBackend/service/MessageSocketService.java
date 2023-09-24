package com.example.discordBackend.service;

import com.example.discordBackend.dtos.message.DirectChatHistory;
import com.example.discordBackend.dtos.message.DirectMessage;
import org.springframework.security.core.Authentication;

public interface MessageSocketService {
    String directMessage(DirectMessage message, Authentication authentication);
    String directChatHistory(DirectChatHistory directChatHistory, Authentication authentication);
}
