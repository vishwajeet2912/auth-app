package com.Substring.auth.controllers;

import com.Substring.auth.Security.JwtService;
import com.Substring.auth.dtos.LoginRequest;
import com.Substring.auth.dtos.TokenResponse;
import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.entities.User;
import com.Substring.auth.repositories.UserRespository;
import com.Substring.auth.services.AuthService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRespository userRespository;
    private final JwtService jwtService;
    private final ModelMapper mapper;

    @PostMapping("/login")
    private ResponseEntity<TokenResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {

        Authentication authentication = authenticate(loginRequest);

        User user = userRespository
                .findByEmail(loginRequest.email())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid Username or password"));

        if (!user.isEnable()) {
            throw new DisabledException("user is disable ");
        }

        String accestoken = jwtService.generateAccessToken(user);

        TokenResponse tokenResponse = TokenResponse.of(
                accestoken,
                "",
                jwtService.getAccessTtlSeconds(),
                mapper.map(user, UserDtos.class)
        );

        return ResponseEntity.ok(tokenResponse);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.email(),
                            loginRequest.password()
                    )
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Username or passwords not valid ");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDtos> registerUser(@RequestBody UserDtos userDtos) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.registerUser(userDtos));
    }
}