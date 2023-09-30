package com.example.discordBackend.dtos.friendInvitation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PendingSenderResPayload {
    String id;
    String username;
    String email;
}
