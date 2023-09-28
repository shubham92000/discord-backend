package com.example.discordBackend.configurations;


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
    private SimpMessagingTemplate simpMessagingTemplate;
    private SocketStore socketStore;
    private FriendSocketService friendSocketService;

    public WebSocketEventListener(SimpMessagingTemplate simpMessagingTemplate, SocketStore socketStore, FriendSocketService friendSocketService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.socketStore = socketStore;
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
        }
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {}
}
