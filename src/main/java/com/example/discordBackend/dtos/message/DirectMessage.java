package com.example.discordBackend.dtos.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DirectMessage {
    private String receiverUserId;
    private String content;
}
