package com.example.discordBackend.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetail {
    private String userId;
    private String username;
    private String email;
}
