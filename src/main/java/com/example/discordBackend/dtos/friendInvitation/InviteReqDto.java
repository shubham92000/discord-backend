package com.example.discordBackend.dtos.friendInvitation;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InviteReqDto {
    @NotEmpty
    private String targetMailAddress;
}
