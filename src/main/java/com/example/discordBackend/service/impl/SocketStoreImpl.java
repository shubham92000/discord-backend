package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.socketStore.*;
import com.example.discordBackend.models.User;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.SocketStore;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SocketStoreImpl implements SocketStore {
    Map<SocketId, UserDetails> activeConnections = new HashMap<>();
    private UserRepo userRepo;

    public SocketStoreImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
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
        User user = userRepo.findByEmail(getOnlineUsersReqDto.getEmail())
                .orElseThrow();

        var friendsEmail = user.getDirectConversationDetails().stream()
                .map(f -> f.getUserDetail().getEmail())
                .collect(Collectors.toSet());

        var onlineFriendsOfUser = new GetOnlineUsersResDto(new ArrayList<>());

        activeConnections.forEach((k, v) -> {
            if(friendsEmail.contains(v.getEmail())){
                onlineFriendsOfUser.getConnectedUsers().add(v.getEmail());
            }
        });

        return new ApiResponse(
                true,
                onlineFriendsOfUser,
                null
        );
    }
}
