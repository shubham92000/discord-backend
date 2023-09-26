package com.example.discordBackend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DirectConversationDetail {
    private String conversationId;
    private UserDetail userDetail;
}
