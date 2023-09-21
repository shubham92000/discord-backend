package com.example.discordBackend.repos;

import com.example.discordBackend.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepo extends MongoRepository<Message, String> {
}
