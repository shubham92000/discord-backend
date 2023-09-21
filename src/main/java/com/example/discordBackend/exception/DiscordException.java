package com.example.discordBackend.exception;

import org.springframework.http.HttpStatus;

public class DiscordException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public DiscordException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public DiscordException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
