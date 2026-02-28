package com.Substring.auth.dtos;

public record TokenResponse(
        String acessToken,
        String freshToken,
        long expiresIn,
        String tokenType,
        UserDtos User
) {
    public TokenResponse(String acessToken,String freshToken,long expiresIn, String tokenType ){
        this(acessToken,freshToken,expiresIn,tokenType,null);
    }
}
