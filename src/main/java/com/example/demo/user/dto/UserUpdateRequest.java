package com.example.demo.user.dto;


import com.example.demo.user.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequest {
    
    @NotBlank private String username;

    @NotBlank @Email private String email;
    
    @NotBlank private String password;

    @NotBlank private Role role;

    public UserUpdateRequest(){};

    public UserUpdateRequest(String username, String email, String password, Role role){
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
