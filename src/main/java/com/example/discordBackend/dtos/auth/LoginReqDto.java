package com.example.discordBackend.dtos.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginReqDto {
    private String email;
    private String password;
}
