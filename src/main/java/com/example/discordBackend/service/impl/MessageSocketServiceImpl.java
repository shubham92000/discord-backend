package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.message.MessagePayload;
import com.example.discordBackend.dtos.socketStore.ChatHistory;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsReqDto;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsResDto;
import com.example.discordBackend.models.Conversation;
import com.example.discordBackend.models.Message;
import com.example.discordBackend.repos.ConversationRepo;
import com.example.discordBackend.repos.MessageRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.MessageSocketService;
import com.example.discordBackend.service.SocketStore;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.discordBackend.utils.WebsocketTopics.chatHistoryTopic;

@Service
public class MessageSocketServiceImpl implements MessageSocketService {
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
    public boolean updateChatHistory(String conversationId, String toSpecifiedSocketId) {
        var conversation = conversationRepo.findById(conversationId)
                .orElseThrow();

        if(toSpecifiedSocketId != null){
            simpMessagingTemplate.convertAndSendToUser(toSpecifiedSocketId, chatHistoryTopic, new com.example.discordBackend.dtos.socketStore.ChatHistory(new ArrayList<>(), new ArrayList<>()));
            return true;
        }

        conversation.getParticipants().forEach(user -> {
            var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(user.getEmail()));
            var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

            sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, chatHistoryTopic, new ChatHistory(new ArrayList<>(), new ArrayList<>())));
        });

        return true;
    }
}
