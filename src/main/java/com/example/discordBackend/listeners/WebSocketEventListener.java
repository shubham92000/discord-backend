package com.example.discordBackend.listeners;


import com.example.discordBackend.dtos.socketStore.GetOnlineUsersReqDto;
import com.example.discordBackend.service.FriendSocketService;
import com.example.discordBackend.service.SocketStore;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Objects;

@Component
public class WebSocketEventListener {
    private FriendSocketService friendSocketService;

    public WebSocketEventListener(FriendSocketService friendSocketService) {
        this.friendSocketService = friendSocketService;
    }

    @EventListener
    private void handleSessionConnect(SessionConnectEvent event) {}

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {}

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String email = headers.getUser().getName();
        String destination = headers.getDestination();

        System.out.println("SessionSubscribeEvent username: "+email);
//        System.out.println("SessionSubscribeEvent destination: "+destination);

        String[] paths = destination.split("/");
        if(paths.length == 5 && Objects.equals(paths[1], "user") && Objects.equals(paths[4], "conversation-list")){
            System.out.println("conversation-list");
            friendSocketService.updateConversations(email);
        }
        else if(paths.length == 5 && Objects.equals(paths[1], "user") && Objects.equals(paths[4], "friends-invitations")){
            System.out.println("friends-invitations");
            friendSocketService.updateFriendsPendingInvitations(email);
        }
        else if(paths.length == 5 && Objects.equals(paths[1], "user") && Objects.equals(paths[4], "online-users")){
            System.out.println("online-users");
            friendSocketService.emitOnlineUsers(email);
        }
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String email = headers.getUser().getName();

        System.out.println("SessionDisconnectEvent username: "+email);
        friendSocketService.emitOfflineUsers(email);
    }
}
