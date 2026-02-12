package com.Substring.auth.services;

import com.Substring.auth.dtos.UserDtos;

public interface AuthService {

    UserDtos registerUser(UserDtos userDtos);

}
