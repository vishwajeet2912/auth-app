package com.Substring.auth.services;

import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.entities.Provider;
import com.Substring.auth.entities.User;
import com.Substring.auth.exceptions.ResourceNotFoundException;
import com.Substring.auth.repositories.UserRespository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRespository userRespository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
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

       User user = userRespository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found with given id "));

       return modelMapper.map(user, UserDtos.class);
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
    @Transactional
    public Iterable<UserDtos> getAllUsers() {

        return userRespository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDtos.class))
                .toList();
    }

}
