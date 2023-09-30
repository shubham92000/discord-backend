package com.example.discordBackend.dtos.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageResPayload {
    private String conversationId;
    private ChatHistoryMessageResPayload message;
}
