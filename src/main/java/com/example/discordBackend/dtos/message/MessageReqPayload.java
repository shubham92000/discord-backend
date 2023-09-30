package com.example.discordBackend.dtos.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageReqPayload {
    private String conversationId;
    private String content;
}
