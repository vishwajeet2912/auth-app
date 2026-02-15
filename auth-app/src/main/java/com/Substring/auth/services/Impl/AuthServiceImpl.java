package com.Substring.auth.services.Impl;

import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.services.AuthService;
import com.Substring.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
   @Override
    public UserDtos registerUser(UserDtos userDtos){


       userDtos.setPassword(passwordEncoder.encode(userDtos.getPassword()));
      // UserDtos userDtos1 = userService.createUser(userDtos);

        return userService.createUser(userDtos);
    }
}
