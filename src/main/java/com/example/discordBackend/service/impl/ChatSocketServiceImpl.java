package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.socketStore.ChatHistory;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsReqDto;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsResDto;
import com.example.discordBackend.exception.DiscordException;
import com.example.discordBackend.repos.ConversationRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.ChatSocketService;
import com.example.discordBackend.service.SocketStore;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.example.discordBackend.utils.WebsocketTopics.*;

@Service
public class ChatSocketServiceImpl implements ChatSocketService {
    private ConversationRepo conversationRepo;
    private SimpMessagingTemplate simpMessagingTemplate;
    private SocketStore socketStore;
    private UserRepo userRepo;

    public ChatSocketServiceImpl(ConversationRepo conversationRepo, SimpMessagingTemplate simpMessagingTemplate, SocketStore socketStore, UserRepo userRepo) {
        this.conversationRepo = conversationRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.socketStore = socketStore;
        this.userRepo = userRepo;
    }

    @Override
    public boolean updateChatHistory(String conversationId, String toSpecifiedSocketId) {
        var conversation = conversationRepo.findById(conversationId)
                .orElseThrow();

        if(toSpecifiedSocketId != null){
            simpMessagingTemplate.convertAndSendToUser(toSpecifiedSocketId, directChatHistory, new ChatHistory(new ArrayList<>(), new ArrayList<>()));
            return true;
        }

        conversation.getParticipants().forEach(user -> {
            var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(user.getEmail()));
            var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

            sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, directChatHistory, new ChatHistory(new ArrayList<>(), new ArrayList<>())));
        });

        return true;
    }

    @Override
    public boolean sendConversations(String email) {
        // send all conversation_id to user ( email )
//        var user = userRepo.findByEmail(email)
//                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, String.format("user with email %s not found", email)));
//
//        var conversationIdList = user.getConversations().stream()
//                .map(conversation -> conversation.getId())
//                .collect(Collectors.toList());
//
//        var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(user.getEmail()));
//        var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();
//
//        sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+conversationIds, conversationIdList));

        return true;
    }
}
