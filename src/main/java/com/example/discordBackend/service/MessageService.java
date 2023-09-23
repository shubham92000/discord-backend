package com.example.discordBackend.service;

import com.example.discordBackend.dtos.message.DirectChatHistory;
import com.example.discordBackend.dtos.message.DirectMessage;

public interface MessageService {
    String directMessage(DirectMessage message);
    String directChatHistory(DirectChatHistory directChatHistory);
}
