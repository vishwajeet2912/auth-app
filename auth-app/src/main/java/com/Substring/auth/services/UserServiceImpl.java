package com.Substring.auth.services;

import com.Substring.auth.dtos.UserDtos;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public UserDtos createUser(UserDtos userDtos) {
        return null;
    }

    @Override
    public UserDtos getUserByemail(String email) {
        return null;
    }

    @Override
    public UserDtos updateUser(UserDtos userDtos, String userId) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public UserDtos getUserById(String userId) {
        return null;
    }

    @Override
    public Iterable<UserDtos> getAllUsers() {
        return null;
    }
}
