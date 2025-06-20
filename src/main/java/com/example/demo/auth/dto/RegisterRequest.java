package com.example.demo.auth.dto;

import com.example.demo.user.Role;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;

    public RegisterRequest(){}

    public RegisterRequest(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername(){ return username; }
    public String getEmail(){ return email; }
    public String getPassword(){ return password; }
    public Role getRole() { return role; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
}
