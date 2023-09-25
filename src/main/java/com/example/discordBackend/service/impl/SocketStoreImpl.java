package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.socketStore.*;
import com.example.discordBackend.service.SocketStore;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.discordBackend.utils.WebsocketTopics.greetings;
import static com.example.discordBackend.utils.WebsocketTopics.topic;

@Service
public class SocketStoreImpl implements SocketStore {
    Map<SocketId, UserDetails> activeConnections = new HashMap<>();
    private SimpMessagingTemplate simpMessagingTemplate;

    public SocketStoreImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public ApiResponse generateSocketId(GenerateSocketIdReqDto generateSocketIdReqDto) {
        String socketId = UUID.randomUUID().toString();
        activeConnections.put(new SocketId(socketId), new UserDetails(generateSocketIdReqDto.getEmail()));
        return new ApiResponse(
                true,
                new GenerateSocketIdResDto(socketId),
                null
        );
    }

    @Override
    public ApiResponse addUser(AddUserReqDto addUserReqDto) {
        activeConnections.putIfAbsent(new SocketId(addUserReqDto.getSocketId()), new UserDetails(addUserReqDto.getEmail()));
        return new ApiResponse(
                true,
                new AddUserResDto(addUserReqDto.getSocketId()),
                null
        );
    }

    @Override
    public ApiResponse removeUser(RemoveUserReqDto removeUserReqDto) {
        activeConnections.remove(new SocketId(removeUserReqDto.getSocketId()));
        return new ApiResponse(
                true,
                new RemoveUserResDto(removeUserReqDto.getEmail()),
                null
        );
    }

    @Override
    public ApiResponse getActiveSocketConnections(GetActiveConnectionsReqDto getActiveConnectionsReqDto) {
        List<String> sockets = new ArrayList<>();
        activeConnections.forEach((k,v) -> {
            if(Objects.equals(v.getEmail(), getActiveConnectionsReqDto.getEmail())){
                sockets.add(k.getId());
            }
        });
        return new ApiResponse(
                true,
                new GetActiveConnectionsResDto(sockets),
                null
        );
    }

    @Override
    public ApiResponse getOnlineUsers(GetOnlineUsersReqDto getOnlineUsersReqDto) {
        return new ApiResponse(
                true,
                new GetOnlineUsersResDto(new ArrayList<>()),
                null
        );
    }

    @Override
    public ApiResponse subscribeComplete(Authentication authentication) {
//        this.simpMessagingTemplate.convertAndSend("/topic/greetings2", "greetings2");
//
//        var email = authentication.getName();
//
//        var response = getActiveSocketConnections(new GetActiveConnectionsReqDto(email));
//        var sockets = ((GetActiveConnectionsResDto)response.getData()).getSockets();
//
//        System.out.println("email: "+email);
//        System.out.println("sockets size :"+sockets.size());
//        sockets.forEach(socket -> {
//            System.out.println("socket: "+socket);
//            simpMessagingTemplate.convertAndSendToUser(socket, "/topic/greetings", "greetings-user");
//        });

        return new ApiResponse(true, null, null);
    }
}
