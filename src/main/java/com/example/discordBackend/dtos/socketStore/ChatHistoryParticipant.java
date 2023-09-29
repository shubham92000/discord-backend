package com.example.discordBackend.dtos.socketStore;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistoryParticipant {
    private String id;
    private String email;
    private String username;
}
