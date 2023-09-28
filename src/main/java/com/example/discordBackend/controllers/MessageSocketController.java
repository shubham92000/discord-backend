package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.message.UpdateChatHistory;
import com.example.discordBackend.dtos.message.MessagePayload;
import com.example.discordBackend.service.MessageSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import static com.example.discordBackend.utils.WebsocketTopics.*;

@Controller
public class MessageSocketController {

    private MessageSocketService messageSocketService;

    public MessageSocketController(MessageSocketService messageSocketService) {
        this.messageSocketService = messageSocketService;
    }

    @MessageMapping(message)
    public void directMessage(MessagePayload message, Authentication authentication){
        messageSocketService.message(message, authentication);
    }

    @MessageMapping(chatHistory)
    public void chatHistory(UpdateChatHistory updateChatHistory, Authentication authentication){
        messageSocketService.updateChatHistory(updateChatHistory.getConversationId(), updateChatHistory.getToSpecifiedSocketId());
    }
}
