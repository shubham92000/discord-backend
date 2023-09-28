package com.example.discordBackend.service.impl;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.service.FriendSocketService;
import com.example.discordBackend.service.SubscriptionService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private FriendSocketService friendSocketService;

    public SubscriptionServiceImpl(FriendSocketService friendSocketService) {
        this.friendSocketService = friendSocketService;
    }

    @Override
    public ApiResponse subscribeComplete(Authentication authentication) {
        var email = authentication.getName();

        // send conversation_list
        friendSocketService.updateConversations(email);

        // send pending friends invitations list
        friendSocketService.updateFriendsPendingInvitations(email);

        // todo -> emit online users

        return new ApiResponse(true, null, null);
    }
}
