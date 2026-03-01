package com.Substring.auth.dtos;

public record LoginRequest(
        String email,
        String password
) {
}
