package com.example.discordBackend.dtos.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatHistoryMessageResPayload {
    private String id;
    private String content;
    private String author;
    private String datetime;
}
