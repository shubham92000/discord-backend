package com.example.discordBackend.dtos.socketStore;

import com.example.discordBackend.models.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistoryMessage {
    private String id;
    private String content;
    private String author;
}
