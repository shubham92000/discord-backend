package com.example.discordBackend.dtos.socketStore;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistory {
    private List<String> messages;
    private List<String> participants;
}
