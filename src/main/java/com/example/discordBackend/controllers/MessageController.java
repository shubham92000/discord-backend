package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.message.DirectChatHistory;
import com.example.discordBackend.dtos.message.DirectMessage;
import com.example.discordBackend.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/direct-message")
    @SendTo("/topic/greetings")
    public String directMessage(DirectMessage message){
        return messageService.directMessage(message);
    }

    @MessageMapping("/direct-chat-history")
    @SendTo("/topic/greetings")
    public String directChatHistory(DirectChatHistory directChatHistory){
        return messageService.directChatHistory(directChatHistory);
    }
}
