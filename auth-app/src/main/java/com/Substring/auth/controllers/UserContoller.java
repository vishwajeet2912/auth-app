package com.Substring.auth.controllers;


import com.Substring.auth.dtos.UserDtos;
import com.Substring.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserContoller {

    private final UserService userService;


    //create user api
    @PostMapping
    public ResponseEntity<UserDtos> createUser (@RequestBody UserDtos userDtos){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDtos));
    }

    // get all user Api
    @GetMapping
    public ResponseEntity<Iterable<UserDtos>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    //get user by email
     @GetMapping ("/email/{email}")
    public ResponseEntity<UserDtos> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(userService.getUserByemail(email));

    }
}
