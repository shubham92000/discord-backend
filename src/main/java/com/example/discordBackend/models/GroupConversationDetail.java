package com.example.discordBackend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupConversationDetail {
    private String conversationId;
    private GroupDetail groupDetail;
}
