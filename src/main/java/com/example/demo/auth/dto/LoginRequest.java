package com.example.demo.auth.dto;

public class LoginRequest {
    private String emailOrUsername;
    private String password;

    public LoginRequest() {}
    public LoginRequest(String emailOrUsername, String password) {
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }

    // Getters
    public String getEmailOrUsername(){ return emailOrUsername; }
    public String getPassword(){ return password; }

    // Setters
    public void setEmailOrUsername(String emailOrUsername) { this.emailOrUsername = emailOrUsername; }
    public void setPassword(String password) { this.password = password; }

    public boolean isEmail(){
        return emailOrUsername != null && emailOrUsername.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}

