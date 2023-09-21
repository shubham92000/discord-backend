package com.example.discordBackend.repos;

import com.example.discordBackend.models.FriendInvitation;
import com.example.discordBackend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FriendInvitationRepo extends MongoRepository<FriendInvitation, String> {
    Optional<FriendInvitation> findBySenderAndReceiver(User sender, User receiver);
}
