package com.example.discordBackend.dtos.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterResDto {
    private String token;
    private String email;
    private String username;
    private String id;
}
