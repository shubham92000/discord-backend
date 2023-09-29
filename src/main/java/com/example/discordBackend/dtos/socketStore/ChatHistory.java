package com.example.discordBackend.dtos.socketStore;

import com.example.discordBackend.utils.ConversationType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistory {
    private String conversationId;
    private List<ChatHistoryParticipant> participants;
    private List<ChatHistoryMessage> messages;
    private ConversationType type;
    private String groupId;
}
