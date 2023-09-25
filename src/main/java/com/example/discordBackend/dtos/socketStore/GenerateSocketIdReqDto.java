package com.example.discordBackend.dtos.socketStore;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenerateSocketIdReqDto {
    @NotEmpty
    private String email;
}
