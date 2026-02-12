package com.Substring.auth.services.Impl;

import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.services.AuthService;
import com.Substring.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

   @Override
    public UserDtos registerUser(UserDtos userDtos){
       UserDtos userDtos1 = userService.createUser(userDtos);

        return userDtos1;
    }
}
