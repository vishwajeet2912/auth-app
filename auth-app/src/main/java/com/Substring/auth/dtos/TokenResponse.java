package com.Substring.auth.dtos;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        long expiresIn,
        String tokenType,
        UserDtos User
) {
    public static TokenResponse of(String accessToken,String refreshToken,long expiresIn, String tokenType ){
        return new TokenResponse(accessToken,refreshToken,expiresIn,"Bearer",null);
    }
}
