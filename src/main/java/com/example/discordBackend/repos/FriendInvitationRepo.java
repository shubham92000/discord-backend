package com.example.discordBackend.repos;

import com.example.discordBackend.models.FriendInvitation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FriendInvitationRepo extends MongoRepository<FriendInvitation, String> {
}
