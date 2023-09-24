package com.example.discordBackend.dtos.socketStore;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetActiveConnectionsReqDto {
    String email;
}
