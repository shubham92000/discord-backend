package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.message.DirectChatHistory;
import com.example.discordBackend.dtos.message.DirectMessage;
import com.example.discordBackend.service.MessageSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import static com.example.discordBackend.utils.WebsocketTopics.*;

@Controller
public class MessageSocketController {

    private MessageSocketService messageSocketService;

    public MessageSocketController(MessageSocketService messageSocketService) {
        this.messageSocketService = messageSocketService;
    }

    @MessageMapping(directMessage)
    public void directMessage(DirectMessage message, Authentication authentication){
        messageSocketService.directMessage(message, authentication);
    }

    @MessageMapping(directChatHistory)
    public void directChatHistory(DirectChatHistory directChatHistory, Authentication authentication){
        messageSocketService.directChatHistory(directChatHistory, authentication);
    }
}
