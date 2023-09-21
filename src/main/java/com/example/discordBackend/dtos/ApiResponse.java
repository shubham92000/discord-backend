package com.example.discordBackend.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse {
    private Object data;
    private ErrorInfo error;
}
