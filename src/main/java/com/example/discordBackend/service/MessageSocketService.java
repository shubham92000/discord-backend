package com.example.discordBackend.service;

import com.example.discordBackend.dtos.message.MessagePayload;
import org.springframework.security.core.Authentication;

public interface MessageSocketService {
    String message(MessagePayload messagePayload, Authentication authentication);
    boolean updateChatHistory(String conversationId, String toSpecifiedSocketId);
}
