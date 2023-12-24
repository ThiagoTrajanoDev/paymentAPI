package com.paymentServiceChallenge.controller;

import com.paymentServiceChallenge.DTOs.UserDTO;
import com.paymentServiceChallenge.domain.user.User;
import com.paymentServiceChallenge.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser( @RequestBody UserDTO user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAlUsers() {
    List<User> allUsers =  this.userService.getAllUsers();
    return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
}
