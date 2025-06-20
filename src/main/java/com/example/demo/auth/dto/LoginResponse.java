package com.example.demo.auth.dto;

import com.example.demo.user.Role;

public class LoginResponse {
    private String token;
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String message;

    public LoginResponse() {}
    public LoginResponse(String token, Long id, String username, String email, Role role, String message) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    //Getters
    public String getToken() { return token; }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public String getMessage() { return message; }

    //Setters
    public void setToken(String token) { this.token = token; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(Role role) { this.role = role; }
    public void setMessage(String message) { this.message = message; }

}
