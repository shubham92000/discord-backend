package com.example.discordBackend.service;

public interface ChatSocketService {
    boolean updateChatHistory(String conversationId, String toSpecifiedSocketId);
}
