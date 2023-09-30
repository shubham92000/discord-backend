package com.example.discordBackend.dtos.message;

import com.example.discordBackend.utils.ConversationType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistoryResPayload {
    private String conversationId;
    private List<ChatHistoryParticipantResPayload> participants;
    private List<ChatHistoryMessageResPayload> messages;
    private ConversationType type;
    private String groupId;
}
