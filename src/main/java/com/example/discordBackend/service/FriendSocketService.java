package com.example.discordBackend.service;

public interface FriendSocketService {
    void updateFriendsPendingInvitations(String email);
    void updateConversations(String email);
}
