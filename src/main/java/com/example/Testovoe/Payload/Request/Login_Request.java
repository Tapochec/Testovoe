package com.example.Testovoe.Payload.Request;

import jakarta.validation.constraints.NotEmpty;

public class Login_Request {
    @NotEmpty(message = "error email")
    private String email;

    @NotEmpty(message = "error password")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
