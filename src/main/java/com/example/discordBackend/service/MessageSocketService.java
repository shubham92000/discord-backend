package com.example.discordBackend.service;

import com.example.discordBackend.dtos.message.MessageReqPayload;
import org.springframework.security.core.Authentication;

public interface MessageSocketService {
    String message(MessageReqPayload messageReqPayload, Authentication authentication);
    boolean chatHistory(String conversationId, String toSpecificUser);
}
