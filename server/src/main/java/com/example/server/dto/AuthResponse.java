package com.example.server.dto;

public record AuthResponse(
        Long userId,
        Long partnerId,
        String token,
        String name
) {}