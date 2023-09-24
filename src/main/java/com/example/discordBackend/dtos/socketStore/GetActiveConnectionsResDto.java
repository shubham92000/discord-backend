package com.example.discordBackend.dtos.socketStore;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetActiveConnectionsResDto {
    List<String> sockets;
}
