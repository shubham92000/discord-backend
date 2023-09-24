package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.socketStore.*;
import com.example.discordBackend.service.SocketStore;

import java.util.*;

public class SocketStoreImpl implements SocketStore {
    Map<SocketId, UserDetails> activeConnections = new HashMap<>();

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
            if(v.getEmail() == getActiveConnectionsReqDto.getEmail()){
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
}
