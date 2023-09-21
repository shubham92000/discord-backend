package com.example.discordBackend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Document(collection = "messages")
public class Message {
    @Id
    private String id;

    private String content;

    @DocumentReference
    private User author;

    private String type;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime lastModifiedOn;

    public Message(){}

    public Message(String content, User author, String type) {
        this.content = content;
        this.author = author;
        this.type = type;
    }
}
