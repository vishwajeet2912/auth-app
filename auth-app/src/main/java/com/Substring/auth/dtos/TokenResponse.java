//package com.Substring.auth.dtos;
//
//public record TokenResponse(
//        String accessToken,
//        String refreshToken,
//        long expiresIn,
//        String tokenType,
//        UserDtos User
//) {
//    public static TokenResponse of(String accessToken,String refreshToken,long expiresIn, String tokenType ){
//        return new TokenResponse(accessToken,refreshToken,expiresIn,"Bearer",null);
//    }
//}
package com.Substring.auth.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private UserDtos user;

    public static TokenResponse of(String accessToken,
                                   String refreshToken,
                                   long expiresIn,
                                   UserDtos user){

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .user(user)
                .build();
    }
}