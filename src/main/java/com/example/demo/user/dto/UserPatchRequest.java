package com.example.demo.user.dto;

import com.example.demo.user.Role;

import jakarta.validation.constraints.Email;

public class UserPatchRequest {
    private String username;

    @Email
    private String email;

    private String password;
    private Role role;

    public UserPatchRequest(){}

    public UserPatchRequest(String username, String email, String password, Role role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername(){ return username; }
    public String getEmail(){ return email; }
    public String getPassword(){ return password; }
    public Role getRole(){ return role; }

}
