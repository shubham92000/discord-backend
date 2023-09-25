package com.example.discordBackend.configurations;


import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsReqDto;
import com.example.discordBackend.dtos.socketStore.GetActiveConnectionsResDto;
import com.example.discordBackend.service.SocketStore;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import static com.example.discordBackend.utils.WebsocketTopics.greetings;
import static com.example.discordBackend.utils.WebsocketTopics.topic;

@Component
public class WebSocketEventListener {
    private SimpMessagingTemplate simpMessagingTemplate;
    private SocketStore socketStore;

    public WebSocketEventListener(SimpMessagingTemplate simpMessagingTemplate, SocketStore socketStore) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.socketStore = socketStore;
    }

    @EventListener
    private void handleSessionConnect(SessionConnectEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String email = headers.getUser().getName();
//        System.out.println("SessionConnectEvent username: "+email);

//        LoginEvent loginEvent = new LoginEvent(username);
//        messagingTemplate.convertAndSend(loginDestination, loginEvent);
//
//        // We store the session as we need to be idempotent in the disconnect event processing
//        participantRepository.add(headers.getSessionId(), loginEvent);
    }

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String email = headers.getUser().getName();
//        System.out.println("SessionConnectedEvent username: "+email);
    }

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String email = headers.getUser().getName();
        System.out.println("SessionSubscribeEvent username: "+email);
        System.out.println("SessionSubscribeEvent message: "+ event.getMessage());

//        var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(email));
//        var sockets = ((GetActiveConnectionsResDto)response.getData()).getSockets();
//
//        System.out.println("sockets size :"+sockets.size());
//        sockets.forEach(socket -> {
//            System.out.println("socket: "+socket);
//            simpMessagingTemplate.convertAndSendToUser(socket, topic+greetings, "yes");
//        });
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
//        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
//        String email = headers.getUser().getName();
//        System.out.println("handleSessionDisconnect username: "+email);
//        System.out.println("handleSessionDisconnect message: "+ event.getMessage());
//
//        var response = socketStore.getActiveSocketConnections(new GetActiveConnectionsReqDto(email));
//        var sockets = ((GetActiveConnectionsResDto)response.getData()).getSockets();

//        System.out.println("sockets size :"+sockets.size());
//        sockets.forEach(socket -> {
//            System.out.println("socket: "+socket);
//            simpMessagingTemplate.convertAndSendToUser(socket, topic+greetings, "yes");
//        });

//        simpMessagingTemplate.convertAndSend("/topic/greetings2", "yesss");
    }
}
