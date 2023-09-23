package com.example.discordBackend.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse {
    private Boolean status;
    private Object data;
    private ErrorInfo error;
}
