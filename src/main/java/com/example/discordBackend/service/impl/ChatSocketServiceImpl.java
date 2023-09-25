package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.socketStore.ChatHistory;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsReqDto;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsResDto;
import com.example.discordBackend.repos.ConversationRepo;
import com.example.discordBackend.service.ChatSocketService;
import com.example.discordBackend.service.SocketStore;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.discordBackend.utils.WebsocketTopics.directChatHistory;

@Service
public class ChatSocketServiceImpl implements ChatSocketService {
    private ConversationRepo conversationRepo;
    private SimpMessagingTemplate simpMessagingTemplate;
    private SocketStore socketStore;

    public ChatSocketServiceImpl(ConversationRepo conversationRepo, SimpMessagingTemplate simpMessagingTemplate, SocketStore socketStore) {
        this.conversationRepo = conversationRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.socketStore = socketStore;
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
}
