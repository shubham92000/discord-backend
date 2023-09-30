package com.example.discordBackend.service;

import java.time.LocalDateTime;

public interface DateTimeParser {
    String toClient(LocalDateTime localDateTime);
}
