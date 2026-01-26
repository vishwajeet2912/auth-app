package com.Substring.auth.services;

import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.entities.Provider;
import com.Substring.auth.entities.User;
import com.Substring.auth.repositories.UserRespository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRespository userRespository;
    private final ModelMapper modelMapper;

    @Override
    public UserDtos createUser(UserDtos userDtos) {

        if (userDtos.getEmail()== null || userDtos.getEmail().isBlank()){
            throw new IllegalArgumentException("Email is requried");
        }
        if (userRespository.existsByEmail(userDtos.getEmail())){
            throw  new IllegalArgumentException("Email already exists");
        }
       User user = modelMapper.map(userDtos, User.class);
        user.setProvider(userDtos.getProvider() != null ? userDtos.getProvider() : Provider.Local);
        User savedUser = userRespository.save(user);
        return modelMapper.map(savedUser, UserDtos.class);
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
