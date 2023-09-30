package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.message.*;
import com.example.discordBackend.dtos.socketStore.*;
import com.example.discordBackend.models.Conversation;
import com.example.discordBackend.models.Message;
import com.example.discordBackend.repos.ConversationRepo;
import com.example.discordBackend.repos.MessageRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.DateTimeParser;
import com.example.discordBackend.service.MessageSocketService;
import com.example.discordBackend.service.SocketStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.discordBackend.utils.WebsocketTopics.*;

@Service
public class MessageSocketServiceImpl implements MessageSocketService {
    private Logger log = LoggerFactory.getLogger(MessageSocketService.class);
    private UserRepo userRepo;
    private MessageRepo messageRepo;
    private ConversationRepo conversationRepo;
    private SimpMessagingTemplate simpMessagingTemplate;
    private SocketStore socketStore;
    private DateTimeParser dateTimeParser;

    public MessageSocketServiceImpl(UserRepo userRepo, MessageRepo messageRepo, ConversationRepo conversationRepo, SimpMessagingTemplate simpMessagingTemplate, SocketStore socketStore, DateTimeParser dateTimeParser) {
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
        this.conversationRepo = conversationRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.socketStore = socketStore;
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public String message(MessageReqPayload messageReqPayload, Authentication authentication) {
        log.info("message received: "+ messageReqPayload);

        var email = authentication.getName();

        var author = userRepo.findByEmail(email)
                .orElseThrow();

        Message message = new Message(messageReqPayload.getContent(), author);
        messageRepo.save(message);

        // find the conversation id
        // add the message in conversation
        Conversation conversation = conversationRepo.findById(messageReqPayload.getConversationId())
                .orElseThrow();

        conversation.getMessages().add(message);
        conversation = conversationRepo.save(conversation);

        final String conversationId = conversation.getId();

        conversation.getParticipants().forEach(user -> {
            var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(user.getEmail()));
            var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

            var chatHistoryMessageResPayload = new ChatHistoryMessageResPayload(
                    message.getId(),
                    message.getContent(),
                    message.getAuthor().getUsername(),
                    dateTimeParser.toClient(message.getCreatedOn())
            );

            sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+messageTopic, new MessageResPayload(
                    conversationId,
                    chatHistoryMessageResPayload
            )));
        });

        return "directMessage";
    }

    @Override
    public boolean chatHistory(String conversationId, String toSpecificUser) {
        var conversation = conversationRepo.findById(conversationId)
                .orElseThrow();

        var participants = conversation.getParticipants().stream().map(p -> new ChatHistoryParticipantResPayload(
                p.getId(), p.getEmail(), p.getUsername()
        )).collect(Collectors.toList());

        var messages = conversation.getMessages().stream().map(m -> new ChatHistoryMessageResPayload(
                m.getId(), m.getContent(), m.getAuthor().getUsername(), dateTimeParser.toClient(m.getCreatedOn())
        )).collect(Collectors.toList());

        if(toSpecificUser != null){
            var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(toSpecificUser));
            var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

            sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+chatHistoryTopic, new ChatHistoryResPayload(
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

            sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+chatHistoryTopic, new ChatHistoryResPayload(
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
