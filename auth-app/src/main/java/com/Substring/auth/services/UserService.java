package com.Substring.auth.services;

import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.entities.User;

public interface UserService {

    UserDtos createUser(UserDtos userDtos);


    UserDtos getUserByEmail(String email);

    UserDtos updateUser(UserDtos userDtos , String userId);

    void deleteUser(String userId);

    UserDtos getUserById(String userId);
    Iterable<UserDtos> getAllUsers();

}
