package com.example.discordBackend.models;

import com.example.discordBackend.utils.ConversationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConversationDetail {
    private String conversationId;
    private ConversationType type;
}
