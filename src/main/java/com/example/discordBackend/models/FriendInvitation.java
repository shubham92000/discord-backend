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
@Document(collection = "friendinvitations")
public class FriendInvitation {
    @Id
    private String id;

    @DocumentReference
    private User senderId;

    @DocumentReference
    private User receiverId;

    @CreatedDate
    private LocalDateTime createdOn;

    @LastModifiedDate
    private LocalDateTime lastModifiedOn;

    public FriendInvitation(){}

    public FriendInvitation(User senderId, User receiverId){
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
}
