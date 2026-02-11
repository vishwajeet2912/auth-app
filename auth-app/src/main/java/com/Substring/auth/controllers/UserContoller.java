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
    // get user by email

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDtos> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    //delete user
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("usedId")String userId){
        userService.deleteUser(userId);
    }
//    //update user
//    @PutMapping("/{userId}")
//    public ResponseEntity<UserDtos> updateUser(@RequestBody UserDtos userDtos, @PathVariable("userId") String userId  ){
//        return ResponseEntity.ok(userService.updateUser(userDtos,userId));
//    }
//    // get user by id
//    @GetMapping("/userId/{userId}")
//    public ResponseEntity<UserDtos> getUserById(@PathVariable("UserId") String userId) {
//        return ResponseEntity.ok(userService.getUserById(userId));
//    }
// Update user
@PutMapping("/{userId}")
public ResponseEntity<UserDtos> updateUser(
        @RequestBody UserDtos userDtos,
        @PathVariable("userId") String userId) {

    return ResponseEntity.ok(userService.updateUser(userDtos, userId));
}

    // Get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDtos> getUserById(
            @PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.getUserById(userId));
    }

}
