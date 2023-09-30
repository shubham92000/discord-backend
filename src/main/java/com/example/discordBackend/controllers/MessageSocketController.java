package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.message.chatHistoryReqPayload;
import com.example.discordBackend.dtos.message.MessageReqPayload;
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

    @MessageMapping(messageTopic)
    public void directMessage(MessageReqPayload message, Authentication authentication){
        messageSocketService.message(message, authentication);
    }

    @MessageMapping(chatHistoryTopic)
    public void chatHistory(chatHistoryReqPayload chatHistoryReqPayload, Authentication authentication){
        messageSocketService.chatHistory(chatHistoryReqPayload.getConversationId(), authentication.getName());
    }
}
