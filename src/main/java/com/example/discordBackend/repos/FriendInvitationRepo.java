package com.example.discordBackend.repos;

import com.example.discordBackend.models.FriendInvitation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FriendInvitationRepo extends MongoRepository<FriendInvitation, String> {
    Optional<FriendInvitation> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
