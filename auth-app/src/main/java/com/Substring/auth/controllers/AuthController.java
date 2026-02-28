package com.Substring.auth.controllers;

import com.Substring.auth.dtos.TokenResponse;
import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.services.AuthService;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

   @PostMapping("/login")
    private ResponseEntity<TokenResponse> login (
            @RequestBody LoginRequest
   ){

    }








    @PostMapping("/register")
    public ResponseEntity<UserDtos> registerUser(@RequestBody UserDtos userDtos){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(userDtos));
    }
}
