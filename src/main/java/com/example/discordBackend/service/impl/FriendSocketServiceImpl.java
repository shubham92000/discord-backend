package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.socketStore.Friend;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsReqDto;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsResDto;
import com.example.discordBackend.dtos.socketStore.PendingSender;
import com.example.discordBackend.exception.DiscordException;
import com.example.discordBackend.repos.FriendInvitationRepo;
import com.example.discordBackend.repos.UserRepo;
import com.example.discordBackend.service.FriendSocketService;
import com.example.discordBackend.service.SocketStore;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.discordBackend.utils.WebsocketTopics.*;

@Service
public class FriendSocketServiceImpl implements FriendSocketService {
    private FriendInvitationRepo friendInvitationRepo;
    private UserRepo userRepo;
    private SocketStore socketStore;
    private SimpMessagingTemplate simpMessagingTemplate;

    public FriendSocketServiceImpl(FriendInvitationRepo friendInvitationRepo, UserRepo userRepo, SocketStore socketStore, SimpMessagingTemplate simpMessagingTemplate) {
        this.friendInvitationRepo = friendInvitationRepo;
        this.userRepo = userRepo;
        this.socketStore = socketStore;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * update the pending friends list for this user ( email ) on frontend with web socket
     */
    @Override
    public void updateFriendsPendingInvitations(String email) {
        var receiver = userRepo.findByEmail(email)
                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, String.format("user with %s not found", email)));

        var pendingInvitations = friendInvitationRepo.findByReceiver(receiver);

        var pendingSenders = pendingInvitations.stream()
                .map(pi -> new PendingSender(pi.getSender().getId(), pi.getSender().getUsername(), pi.getSender().getEmail()))
                .collect(Collectors.toList());

        var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(email));
        var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

        sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+friendsInvitations, pendingSenders));
    }

    /**
     * update the list of friends for this user ( email ) on frontend with web socket
     * @param email
     */
    @Override
    public void updateFriends(String email) {
        var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(email));
        var sockets = ((GetActiveConnectionsResDto) response.getData()).getSockets();

        if(sockets.size() == 0) return;

        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new DiscordException(HttpStatus.NOT_FOUND, String.format("user with %s not found", email)));

        // list of friends for this user (email)
        var friends = user.getFriends().stream().map(f -> new Friend(f.getId(), f.getEmail(), f.getUsername()))
                        .collect(Collectors.toList());

        sockets.forEach(socket -> simpMessagingTemplate.convertAndSendToUser(socket, topic+friendsList, friends));
    }
}
