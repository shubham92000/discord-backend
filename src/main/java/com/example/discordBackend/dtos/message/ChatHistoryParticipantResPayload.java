package com.example.discordBackend.dtos.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistoryParticipantResPayload {
    private String id;
    private String email;
    private String username;
}
