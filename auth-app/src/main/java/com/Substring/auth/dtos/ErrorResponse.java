package com.Substring.auth.dtos;


import org.springframework.http.HttpStatus;

public record ErrorResponse(
            String message ,
            HttpStatus status
//            int status,
//            String error // int statud and String error instead of this we can write simply HttpStatus status and in globalexception file you can write HttpStstus.notfound
    ){


}
