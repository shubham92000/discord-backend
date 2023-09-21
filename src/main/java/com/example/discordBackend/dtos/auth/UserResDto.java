package com.example.discordBackend.dtos.auth;

import com.example.discordBackend.utils.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResDto {
    private String id;
    private String name;
    private String email;
    private List<Role> roles;
}
