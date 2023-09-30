package com.example.discordBackend.service.impl;

import com.example.discordBackend.service.DateTimeParser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeParserImpl implements DateTimeParser {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public
    String toClient(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }
}
