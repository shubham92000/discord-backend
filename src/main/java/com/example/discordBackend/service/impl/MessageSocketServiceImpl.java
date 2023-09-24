package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.message.DirectChatHistory;
import com.example.discordBackend.dtos.message.DirectMessage;
import com.example.discordBackend.service.MessageSocketService;
import org.springframework.stereotype.Service;

@Service
public class MessageSocketServiceImpl implements MessageSocketService {
    @Override
    public String directMessage(DirectMessage message) {
        return "directMessage";
    }

    @Override
    public String directChatHistory(DirectChatHistory directChatHistory) {
        return "directChatHistory";
    }
}
