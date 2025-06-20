package com.example.demo.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.dto.UserPatchRequest;
import com.example.demo.user.dto.UserUpdateRequest;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PatchMapping
    public User patchUser(@RequestBody UserPatchRequest request){
        return userService.patchUser(request);
    }

    @PutMapping
    public User updateUser( @RequestBody @Valid UserUpdateRequest request) {
        return userService.updateUser(request);
    }
}
