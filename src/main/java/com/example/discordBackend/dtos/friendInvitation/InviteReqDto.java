package com.example.discordBackend.dtos.friendInvitation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InviteReqDto {
    private String targetMailAddress;
}
