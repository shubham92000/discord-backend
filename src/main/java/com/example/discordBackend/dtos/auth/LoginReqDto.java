package com.example.discordBackend.dtos.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginReqDto {
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
