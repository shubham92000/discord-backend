package com.example.discordBackend.repos;

import com.example.discordBackend.models.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConversationRepo extends MongoRepository<Conversation, String> {
}
