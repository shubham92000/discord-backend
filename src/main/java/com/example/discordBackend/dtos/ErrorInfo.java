package com.example.discordBackend.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorInfo {
    private String code;
    private String message;
}
