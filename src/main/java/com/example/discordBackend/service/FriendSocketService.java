package com.example.discordBackend.service;

public interface FriendSocketService {
    void updateFriendsPendingInvitations(String email);
    void updateConversations(String email);
    void emitOnlineUsers(String email);
    void emitOfflineUsers(String email);
}
