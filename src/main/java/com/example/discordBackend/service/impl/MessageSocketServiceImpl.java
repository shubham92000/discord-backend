package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.message.MessagePayload;
import com.example.discordBackend.dtos.socketStore.*;
import com.example.discordBackend.models.Conversation;
import com.example.discordBackend.models.Message;
import com.example.discordBackend.repos.ConversationRepo;
import com.example.discordBackend.repos.MessageRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.MessageSocketService;
import com.example.discordBackend.service.SocketStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.example.discordBackend.utils.WebsocketTopics.chatHistoryTopic;
import static com.example.discordBackend.utils.WebsocketTopics.topic;

@Service
public class MessageSocketServiceImpl implements MessageSocketService {
    private Logger log = LoggerFactory.getLogger(MessageSocketService.class);
    private UserRepo userRepo;
    private MessageRepo messageRepo;
    private ConversationRepo conversationRepo;
    private SimpMessagingTemplate simpMessagingTemplate;
    private SocketStore socketStore;

    public MessageSocketServiceImpl(UserRepo userRepo, MessageRepo messageRepo, ConversationRepo conversationRepo, SimpMessagingTemplate simpMessagingTemplate, SocketStore socketStore) {
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
        this.conversationRepo = conversationRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.socketStore = socketStore;
    }

    @Override
    public String message(MessagePayload messagePayload, Authentication authentication) {
        log.info("message received: "+messagePayload);

        var email = authentication.getName();

        var author = userRepo.findByEmail(email)
                .orElseThrow();

        Message message = new Message(messagePayload.getContent(), author);
        messageRepo.save(message);

        // find the conversation id
        // add the message in conversation
        Conversation conversation = conversationRepo.findById(messagePayload.getConversationId())
                .orElseThrow();

        conversation.getMessages().add(message);
        conversation = conversationRepo.save(conversation);

        // updateChatHistory(conversation id)
        updateChatHistory(conversation.getId(), null);

        return "directMessage";
    }

    @Override
    public boolean updateChatHistory(String conversationId, String toSpecificUser) {
        var conversation = conversationRepo.findById(conversationId)
                .orElseThrow();

        var participants = conversation.getParticipants().stream().map(p -> new ChatHistoryParticipant(
                p.getId(), p.getEmail(), p.getUsername()
        )).collect(Collectors.toList());

        var messages = conversation.getMessages().stream().map(m -> new ChatHistoryMessage(
                m.getId(), m.getContent(), m.getAuthor().getUsername()
        )).collect(Collectors.toList());

        if(toSpecificUser != null){
            var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(toSpecificUser));
            var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

            sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+chatHistoryTopic, new ChatHistory(
                    conversation.getId(),
                    participants,
                    messages,
                    conversation.getType(),
                    conversation.getGroupId()
            )));
            return true;
        }

        conversation.getParticipants().forEach(user -> {
            var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(user.getEmail()));
            var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

            sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+chatHistoryTopic, new ChatHistory(
                    conversation.getId(),
                    participants,
                    messages,
                    conversation.getType(),
                    conversation.getGroupId()
            )));
        });

        return true;
    }
}
