package com.example.discordBackend.controllers;

import com.example.discordBackend.dtos.message.DirectChatHistory;
import com.example.discordBackend.dtos.message.DirectMessage;
import com.example.discordBackend.service.MessageSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import static com.example.discordBackend.utils.WebsocketTopics.*;

@Controller
public class MessageSocketController {

    private MessageSocketService messageSocketService;
    private SimpMessagingTemplate simpMessagingTemplate;

    public MessageSocketController(MessageSocketService messageSocketService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageSocketService = messageSocketService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(directMessage)
//    @SendTo(topic+greetings)
    public void directMessage(DirectMessage message){
        this.simpMessagingTemplate.convertAndSend(topic+greetings, messageSocketService.directMessage(message));
//        return messageSocketService.directMessage(message);
    }

//    @MessageMapping(directChatHistory)
//    @SendTo(topic+directChatHistory)
//    public String directChatHistory(DirectChatHistory directChatHistory){
//        return messageSocketService.directChatHistory(directChatHistory);
//    }
}
