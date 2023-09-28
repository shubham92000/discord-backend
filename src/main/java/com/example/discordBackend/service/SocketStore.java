package com.example.discordBackend.service;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.socketStore.*;
import org.springframework.security.core.Authentication;

public interface SocketStore {
    ApiResponse generateSocketId(GenerateSocketIdReqDto generateSocketIdReqDto);
    ApiResponse addUser(AddUserReqDto addUserReqDto);
    ApiResponse removeUser(RemoveUserReqDto removeUserReqDto);
    ApiResponse getActiveSocketConnections(GetActiveConnectionsReqDto getActiveConnectionsReqDto);
    ApiResponse getOnlineUsers(GetOnlineUsersReqDto getOnlineUsersReqDto);
}
