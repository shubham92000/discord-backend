package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.message.DirectChatHistory;
import com.example.discordBackend.dtos.message.DirectMessage;
import com.example.discordBackend.models.Message;
import com.example.discordBackend.repos.MessageRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.ChatSocketService;
import com.example.discordBackend.service.MessageSocketService;
import com.example.discordBackend.utils.MessageType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MessageSocketServiceImpl implements MessageSocketService {
    private ChatSocketService chatSocketService;
    private UserRepo userRepo;
    private MessageRepo messageRepo;

    public MessageSocketServiceImpl(ChatSocketService chatSocketService, UserRepo userRepo, MessageRepo messageRepo) {
        this.chatSocketService = chatSocketService;
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
    }

    @Override
    public String directMessage(DirectMessage directMessage, Authentication authentication) {
        var email = authentication.getName();

        var author = userRepo.findByEmail(directMessage.getReceiverUserId())
                .orElseThrow();

        Message message = new Message(directMessage.getContent(), author, MessageType.DIRECT.toString());
        messageRepo.save(message);

        // find the conversation id
        // add the message in conversation

        // updateChatHistory(conversation id)

        return "directMessage";
    }

    @Override
    public String directChatHistory(DirectChatHistory directChatHistory, Authentication authentication) {
        var email = authentication.getName();

        // find conversation

        // updateChatHistory(conversation id)
        return "directChatHistory";
    }
}
