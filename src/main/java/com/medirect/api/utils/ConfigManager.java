package com.medirect.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigManager {
    private static final ConfigDto config;

    static {
        try {
            InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config.json");
            ObjectMapper objectMapper = new ObjectMapper();
            config = objectMapper.readValue(inputStream, ConfigDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage());
        }
    }

    public static ConfigDto config() {
        return config;
    }
}
